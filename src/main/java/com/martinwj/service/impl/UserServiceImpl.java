package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.activate.IActivateDAO;
import com.martinwj.dao.user.IUserDAO;
import com.martinwj.dao.userProfile.IUserProfileDAO;
import com.martinwj.dao.web.IWebDAO;
import com.martinwj.entity.Activate;
import com.martinwj.entity.User;
import com.martinwj.entity.UserProfile;
import com.martinwj.entity.Web;
import com.martinwj.exception.SysException;
import com.martinwj.service.ActivateService;
import com.martinwj.service.EmailService;
import com.martinwj.service.UserService;
import com.martinwj.util.GetIp;
import com.martinwj.util.Jiami;
import com.martinwj.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.mail.internet.ParseException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName: UserServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserDAO iUserDAO;

    @Autowired
    private IUserProfileDAO iUserProfileDAO;

    @Autowired
    private IWebDAO iWebDAO;

    @Autowired
    private EmailService emailService;

    @Autowired
    private IActivateDAO iActivateDAO;
    @Autowired
    private ActivateService activateService;

    /**
     * 校验用户登录
     *
     * @param loginName 登录名
     * @param passWord  登录密码
     * @return
     */
    public User selectUser(String loginName, String passWord) {
        return iUserDAO.selectUser(loginName, passWord);
    }

    /**
     * 校验账号是否已经被注册
     *
     * @param loginName 注册时填写的账号
     * @param email     注册时填写的邮箱
     * @return
     */
    public int countUser(String loginName, String email) {
        return iUserDAO.countUser(loginName, email);
    }

    /**
     * 注册新用户
     *
     * @return
     * @throws SysException
     */
    public Map<String, Object> register(HttpServletRequest request) throws SysException {
        // 1.0 获取注册参数
        String szLoginName = request.getParameter("loginName");
        String szPassWord = request.getParameter("passWord");
        String szEmail = request.getParameter("email");

        // 2.0 校验注册信息
        // 2.1 校验账号是否填写
        if (StringUtils.isEmpty(szLoginName)) {
            throw new SysException(ErrorMsg.ERROR_100004);
        }
        // 2.2 校验账号长度
        szLoginName = szLoginName.replaceAll("\\s*", "");
        if (szLoginName.length() < 4 || szLoginName.length() > 10) {
            throw new SysException(ErrorMsg.ERROR_100005);
        }

        // 2.3 校验密码是否填写
        if (StringUtils.isEmpty(szPassWord)) {
            throw new SysException(ErrorMsg.ERROR_100006);
        }
        // 2.4 校验密码长度
        szPassWord = szPassWord.replaceAll("\\s*", "");
        if (szPassWord.length() < 6 || szPassWord.length() > 16) {
            throw new SysException(ErrorMsg.ERROR_100007);
        }

        // 2.5 校验邮箱是否填写
        if (StringUtils.isEmpty(szEmail)) {
            throw new SysException(ErrorMsg.ERROR_100008);
        }

        int count = 0;
        // 2.6 校验账号是否已被占用
        count = countUser(szLoginName, null);
        if (count > 0) {
            throw new SysException(ErrorMsg.ERROR_100009);
        }
        // 2.7 校验邮箱是否已被占用
        szEmail = szEmail.replaceAll("\\s*", "").toLowerCase();
        count = countUser(null, szEmail);
        if (count > 0) {
            throw new SysException(ErrorMsg.ERROR_100010);
        }

        String szUserIp = GetIp.getIp(request);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        User user = new User();
        user.setLoginName(szLoginName);
        user.setPassWord(MD5.md5(szPassWord));
        user.setEmail(szEmail);
        user.setRegisterTime(now);
        user.setRegisterIp(szUserIp);
        user.setLastLoginTime(now);
        user.setLastLoginIp(szUserIp);
        user.setStatus("0");    // 账号未激活

        iUserDAO.insert(user);
        user = iUserDAO.selectUserByEmail(szEmail);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setGroupId("2");    // 注册会员
        userProfile.setPoint(0);        // 积分0份
        System.out.println(userProfile);
        iUserProfileDAO.insert(userProfile);

        // 4.0 对用户信息进行加密，用于cookie存储
        // 用户id
        String UID = user.getId();
        // 用户的登录名和密码
        String userToken = Jiami.getInstance().encrypt(szLoginName) + "&&" + Jiami.getInstance().encrypt(szPassWord);
        // 将用户名转为没有特殊字符的base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        userToken = encoder.encode(userToken.getBytes());

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("UID", UID);
        info.put("userToken", userToken);

        return info;
    }

    /**
     * 用户登录
     *
     * @param request
     * @return
     * @throws SysException
     */
    public Map<String, Object> login(HttpServletRequest request) throws SysException {
        // 1.0 获取登录参数
        String szLoginName = request.getParameter("loginName");
        String szPassWord = request.getParameter("passWord");

        // 2.0 校验用户
        // 2.1 校验用户名或密码是否填写
        if (StringUtils.isEmpty(szLoginName) || StringUtils.isEmpty(szPassWord)) {
            throw new SysException(ErrorMsg.ERROR_100001);
        }

        // 2.2 校验用户名、密码是否正确
        User user = selectUser(szLoginName, MD5.md5(szPassWord));
        if (user == null) {
            throw new SysException(ErrorMsg.ERROR_100002);
        }

        // 2.3 校验用户是否正常
        if ("0".equals(user.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100019);
        }
        if ("2".equals(user.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100003);
        }

        // 3.0 将用户信息保存进session
        request.getSession().setAttribute("user", user);

        // 4.0 对用户信息进行加密，用于cookie存储
        // 用户id
        String UID = user.getId();

        // 用户的登录名和密码
        String userToken = Jiami.getInstance().encrypt(szLoginName) + "&&" + Jiami.getInstance().encrypt(szPassWord);
        // 将用户名转为没有特殊字符的base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        userToken = encoder.encode(userToken.getBytes());

        Map<String, Object> info = new HashMap<String, Object>();
        info.put("UID", UID);
        info.put("userToken", userToken);

        return info;
    }


    /**
     * 根据userToken，获取用户信息
     *
     * @param userToken
     * @return
     * @throws SysException
     */
    public User getUserByUserToken(String userToken) throws SysException {
        // userToken编码转换
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoderBase64;
        try {
            decoderBase64 = decoder.decodeBuffer(userToken);
            userToken = new String(decoderBase64);
        } catch (IOException e) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        String[] arr = userToken.split("&&");
        if (arr.length <= 1) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        // userToken解密
        String szLoginName = Jiami.getInstance().decrypt(arr[0]);
        String szPassWord = Jiami.getInstance().decrypt(arr[1]);

        // 根据主键查询用户信息
        User user = selectUser(szLoginName, MD5.md5(szPassWord));
        if (user == null) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        return user;
    }

    /**
     * 根据userToken，自动登录
     *
     * @param userToken
     * @return
     * @throws SysException
     */
    public User getUserInfoByUserToken(String userToken) throws SysException {
        // userToken编码转换
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] decoderBase64;
        try {
            decoderBase64 = decoder.decodeBuffer(userToken);
            userToken = new String(decoderBase64);
        } catch (IOException e) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        String[] arr = userToken.split("&&");
        if (arr.length <= 1) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        // userToken解密
        String szLoginName = Jiami.getInstance().decrypt(arr[0]);
        String szPassWord = Jiami.getInstance().decrypt(arr[1]);

        // 根据主键查询用户信息
        User user = selectUser(szLoginName, MD5.md5(szPassWord));
        if (user == null) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }
        // 校验用户是否正常
        if ("0".equals(user.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100019);
        }
        if ("2".equals(user.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100003);
        }

        return user;
    }

    /**
     * 用户换绑邮箱验证
     *
     * @param user    用户信息
     * @param subject 邮件标题
     * @param type    验证类型
     * @throws SysException
     */
    public void sendEmail(User user, String subject, String type) throws SysException {
        // 获取站点信息
        Web web = iWebDAO.selectWebInfo();
        String webName = web.getName();

        // 获取当前系统时间
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        String verificationCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        // 生成邮件内容
        StringBuffer content = new StringBuffer();
        content.append("<div style='background:#f7f7f7;overflow:hidden'>");
        content.append("<div style='background:#fff;border:1px solid #ccc;margin:2%;padding:0 30px'>");
        content.append("<div style='line-height:40px;height:40px'>&nbsp;</div>");
        content.append("<p style='margin:0;padding:0;font-size:14px;line-height:30px;color:#333;font-family:arial,sans-serif;font-weight:bold'>亲爱的用户 <b style='font-size:18px;color:#f90'>" + user.getLoginName() + "</b>：</p>");
        content.append("<div style='line-height:20px;height:20px'>&nbsp;</div>");
        content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>您好！感谢您使用" + webName + "服务，您正在进行邮箱验证，本次请求的验证码为：</p>");
        content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>");
        content.append("<b style='font-size:18px;color:#f90'>" + verificationCode + "</b>");
        content.append("<span style='margin:0;padding:0;margin-left:10px;line-height:30px;font-size:14px;color:#979797;font-family:'宋体',arial,sans-serif'>(为了保障您账号的安全性，请在1小时内完成验证。)</span>");
        content.append("</p>");
        content.append("<div style='line-height:80px;height:80px'>&nbsp;</div>");
        content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>" + webName + "团队</p>");
        content.append("<p style='margin:0;padding:0;line-height:30px;font-size:14px;color:#333;font-family:'宋体',arial,sans-serif'>" + now + "</p>");
        content.append("</div>");
        content.append("</div>");

        // 发送验证码邮件
        emailService.sendEmail(user.getEmail(), subject, content.toString());

        // 生成验证码有效期
        Activate activate = new Activate();
        activate.setUserId(user.getId());
        activate.setType(type);    // 用户修改邮箱
        activate.setCode(verificationCode);
        activate.setCreateTime(now);

        activateService.save(activate);
    }


    /**
     * 页面一加载就获取用户信息
     *
     * @param request
     * @return
     */
    public User getUser(HttpServletRequest request) {
        String userToken = "";
        Cookie[] cookieArr = request.getCookies();
        if (cookieArr != null && cookieArr.length > 0) {
            for (int i = 0; i < cookieArr.length; i++) {
                Cookie cookie = cookieArr[i];
                if ("userToken".equals(cookie.getName())) {
                    try {
                        userToken = URLDecoder.decode(cookie.getValue(), "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        break;
                    }
                    break;
                }
            }
        }

        if (!StringUtils.isEmpty(userToken)) {
            // 判断session
            HttpSession session = request.getSession();
            // 从session中取出用户身份信息
            User user = (User) session.getAttribute("user");

            if (user == null) {
                try {
                    user = getUserByUserToken(userToken);
                    // 将用户信息保存进session
                    request.getSession().setAttribute("user", user);
                } catch (SysException e) {
                    // 用户凭证是伪造的
                    return null;
                }
            }

            return user;
        }

        return null;
    }

    /**
     * 保存用户信息
     *
     * @param user
     * @throws SysException
     */
    public void save(User user) throws SysException {
        if (StringUtils.isEmpty(user.getId())) {
            // 新增
            // 1.0 获取注册参数
            String szLoginName = user.getLoginName();
            String szPassWord = user.getPassWord();
            String szEmail = user.getEmail();

            // 2.0 校验注册信息
            // 2.1 校验账号是否填写
            if (StringUtils.isEmpty(szLoginName)) {
                throw new SysException(ErrorMsg.ERROR_100004);
            }
            // 2.2 校验账号长度
            szLoginName = szLoginName.replaceAll("\\s*", "");
            if (szLoginName.length() < 4 || szLoginName.length() > 10) {
                throw new SysException(ErrorMsg.ERROR_100005);
            }

            // 2.3 校验密码是否填写
            if (StringUtils.isEmpty(szPassWord)) {
                throw new SysException(ErrorMsg.ERROR_100006);
            }
            // 2.4 校验密码长度
            szPassWord = szPassWord.replaceAll("\\s*", "");
            if (szPassWord.length() < 6 || szPassWord.length() > 16) {
                throw new SysException(ErrorMsg.ERROR_100007);
            }

            // 2.5 校验邮箱是否填写
            if (StringUtils.isEmpty(szEmail)) {
                throw new SysException(ErrorMsg.ERROR_100008);
            }

            int count = 0;
            // 2.6 校验账号是否已被占用
            count = countUser(szLoginName, null);
            if (count > 0) {
                throw new SysException(ErrorMsg.ERROR_100009);
            }
            // 2.7 校验邮箱是否已被占用
            szEmail = szEmail.replaceAll("\\s*", "").toLowerCase();
            count = countUser(null, szEmail);
            if (count > 0) {
                throw new SysException(ErrorMsg.ERROR_100010);
            }

            // 3.0 注册新用户
            // 获取当前时间
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String now = formatter.format(currentTime);

            // 注册时间
            if (StringUtils.isEmpty(user.getRegisterTime())) {
                user.setRegisterTime(now);
            }

            // 上次访问时间
            if (StringUtils.isEmpty(user.getLastLoginTime())) {
                user.setLastLoginTime(now);
            }

            user.setPassWord(MD5.md5(szPassWord));    // 密码加密
            user.setStatus("1");    // 正常状态

            iUserDAO.insert(user);

            UserProfile UserProfile = new UserProfile();
            UserProfile.setUserId(user.getId());
            UserProfile.setAvatar(user.getAvatar());
            UserProfile.setGroupId(user.getGroupId());
            UserProfile.setSignPersonal(user.getSignPersonal());
            UserProfile.setPoint(user.getPoint());

            iUserProfileDAO.insert(UserProfile);
        } else {
            // 编辑
            if (!StringUtils.isEmpty(user.getPassWord())) {
                user.setPassWord(MD5.md5(user.getPassWord()));    // 密码加密
                iUserDAO.update(user);
            }

            UserProfile userProfile = new UserProfile();
            userProfile.setUserId(user.getId());
            userProfile.setAvatar(user.getAvatar());
            userProfile.setGroupId(user.getGroupId());
            userProfile.setSignPersonal(user.getSignPersonal());
            userProfile.setPoint(user.getPoint());

            iUserProfileDAO.update(userProfile);
        }
    }

    /**
     * 邮箱验证，激活账号
     *
     * @param user
     * @param identifyingCode
     * @throws SysException
     * @throws ParseException
     */
    public void validateEmail(User user, String identifyingCode) throws SysException, ParseException {
        // 1.0 获取验证记录
        Activate activate = iActivateDAO.selectByUserIdAndType(user.getId(), "register");
        if (activate == null) {
            throw new SysException(ErrorMsg.ERROR_X00002);
        }

        // 2.0 校验
        // 2.1 校验验证码是否正确
        if (!identifyingCode.equals(activate.getCode())) {
            throw new SysException(ErrorMsg.ERROR_100014);
        }

        // 2.2 校验验证码是否已过期
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(now);
            d2 = df.parse(activate.getCreateTime());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        assert d1 != null;
        assert d2 != null;
        long diff = d1.getTime() - d2.getTime();
        long hours = diff / (1000 * 60 * 60);
        System.out.println("diff: " + diff);
        System.out.println("hours: " + hours);
        if (hours > 0) {
            throw new SysException(ErrorMsg.ERROR_100015);
        }

        // 3.0 修改用户状态
        iUserDAO.update(user);

        // 4.0 删除验证记录
        iActivateDAO.delete(activate.getId());
    }

    /**
     * 用户找回密码，发送邮箱验证码
     * @param email 用户填写的邮箱地址
     * @throws SysException
     */
    public void findPwdEmail(String email) throws SysException {

        if (StringUtils.isEmpty(email)) {
            throw new SysException(ErrorMsg.ERROR_100008);
        }

        // 校验邮箱是否存在
        User user = iUserDAO.selectUserByEmail(email);
        if (user==null) {
            throw new SysException(ErrorMsg.ERROR_100018);
        }

        sendEmail(user, "找回密码验证", "find_pwd");
    }

    /**
     * 用户找回密码，校验验证码
     * @param email 邮箱
     * @param identifyingCode 验证码
     * @throws SysException
     * @throws ParseException
     */
    public void findPwdCode(String email, String identifyingCode) throws SysException, ParseException {
        // 1.0 校验验证码是否正确
        Activate activate = iActivateDAO.selectByEmailAndCodeAndType(email, identifyingCode, "find_pwd");
        if (activate==null) {
            throw new SysException(ErrorMsg.ERROR_100014);
        }

        // 2.0 校验验证码是否已过期
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = df.parse(now);
            d2 = df.parse(activate.getCreateTime());
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        assert d1 != null;
        assert d2 != null;
        long diff = d1.getTime() - d2.getTime();
        long hours = diff / (1000 * 60 * 60);
		System.out.println("diff: "+diff);
		System.out.println("hours: "+hours);
        if (hours>0) {
            throw new SysException(ErrorMsg.ERROR_100015);
        }
    }


}
