package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.user.IUserDAO;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.UserService;
import com.martinwj.util.Jiami;
import com.martinwj.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
     * 注册新用户
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

        // 2.2 校验账号长度


        // 2.3 校验密码是否填写

        // 2.4 校验密码长度


        // 2.5 校验邮箱是否填写

        // 2.6 校验账号是否已被占用

        // 2.7 校验邮箱是否已被占用


        // 3.0 注册新用户

        // 账号未激活

        // 注册会员
        // 积分0份


        // 4.0 对用户信息进行加密，用于cookie存储
        // 用户id
        // 用户的登录名和密码
        // 将用户名转为没有特殊字符的base64编码

        return null;
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

        // 2.2 校验用户名、密码是否正确


        // 2.3 校验用户是否正常


        // 3.0 将用户信息保存进session


        // 4.0 对用户信息进行加密，用于cookie存储
        // 用户id

        // 用户的登录名和密码
        // 将用户名转为没有特殊字符的base64编码


        return null;
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
        User userInfo = selectUser(szLoginName, MD5.md5(szPassWord));
        if (userInfo == null) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        return userInfo;
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
        User userInfo = selectUser(szLoginName, MD5.md5(szPassWord));
        if (userInfo == null) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }
        // 校验用户是否正常
        if ("0".equals(userInfo.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100019);
        }
        if ("2".equals(userInfo.getStatus())) {
            throw new SysException(ErrorMsg.ERROR_100003);
        }

        return userInfo;
    }


    /**
     * 页面一加载就获取用户信息
     *
     * @param request
     * @return
     */
    public User getUserInfo(HttpServletRequest request) {
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
            User user = (User) session.getAttribute("userInfo");

            if (user == null) {
                try {
                    user = getUserByUserToken(userToken);
                    // 将用户信息保存进session
                    request.getSession().setAttribute("userInfo", user);
                } catch (SysException e) {
                    // 用户凭证是伪造的
                    return null;
                }
            }

            return user;
        }

        return null;
    }

}
