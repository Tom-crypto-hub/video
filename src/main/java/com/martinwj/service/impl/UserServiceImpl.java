package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.user.IUserDAO;
import com.martinwj.dao.userProfile.IUserProfileDAO;
import com.martinwj.entity.User;
import com.martinwj.entity.UserProfile;
import com.martinwj.exception.SysException;
import com.martinwj.service.UserService;
import com.martinwj.util.GetIp;
import com.martinwj.util.Jiami;
import com.martinwj.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
        user.setStatus("0");	// 账号未激活

        iUserDAO.insert(user);

        UserProfile userProfile = new UserProfile();
        userProfile.setUserId(user.getId());
        userProfile.setGroupId("2");	// 注册会员
        userProfile.setPoint(0);		// 积分0份
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
            if (szLoginName.length()<4 || szLoginName.length()>10) {
                throw new SysException(ErrorMsg.ERROR_100005);
            }

            // 2.3 校验密码是否填写
            if (StringUtils.isEmpty(szPassWord)) {
                throw new SysException(ErrorMsg.ERROR_100006);
            }
            // 2.4 校验密码长度
            szPassWord = szPassWord.replaceAll("\\s*", "");
            if (szPassWord.length()<6 || szPassWord.length()>16) {
                throw new SysException(ErrorMsg.ERROR_100007);
            }

            // 2.5 校验邮箱是否填写
            if (StringUtils.isEmpty(szEmail)) {
                throw new SysException(ErrorMsg.ERROR_100008);
            }

            int count = 0;
            // 2.6 校验账号是否已被占用
            count = countUser(szLoginName, null);
            if (count>0) {
                throw new SysException(ErrorMsg.ERROR_100009);
            }
            // 2.7 校验邮箱是否已被占用
            szEmail = szEmail.replaceAll("\\s*", "").toLowerCase();
            count = countUser(null, szEmail);
            if (count>0) {
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

            user.setPassWord(MD5.md5(szPassWord));	// 密码加密
            user.setStatus("1");	// 正常状态

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
                user.setPassWord(MD5.md5(user.getPassWord()));	// 密码加密
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

    @Override
    public void update(User user) throws SysException {
        iUserDAO.update(user);
    }

}
