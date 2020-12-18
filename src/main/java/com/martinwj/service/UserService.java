package com.martinwj.service;

import com.martinwj.entity.User;
import com.martinwj.exception.SysException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface UserService {

    /**
     * 校验用户登录
     *
     * @param loginName 登录名
     * @param passWord  登录密码
     * @return
     */
    public User selectUser(String loginName, String passWord);

    /**
     * 注册新用户
     * @param request
     * @return
     * @throws SysException
     */
    public Map<String, Object> register(HttpServletRequest request) throws SysException;

    /**
     * 根据userToken，获取用户信息
     *
     * @param userToken
     * @return
     * @throws SysException
     */
    public User getUserByUserToken(String userToken) throws SysException;

    /**
     * 用户登录
     * @param request
     * @return
     * @throws SysException
     */
    public Map<String, Object> login(HttpServletRequest request) throws SysException ;

    /**
     * 根据userToken，自动登录
     *
     * @param userToken
     * @return
     * @throws SysException
     */
    public User getUserInfoByUserToken(String userToken) throws SysException;

    /**
     * 页面一加载就获取用户信息
     * @param request
     * @return
     */
    public User getUserInfo(HttpServletRequest request);

}
