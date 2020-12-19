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
     * 校验账号是否已经被注册
     * @param loginName 注册时填写的账号
     * @param email 注册时填写的邮箱
     * @return
     */
    public int countUser(String loginName, String email);

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
    public User getUser(HttpServletRequest request);

    /**
     * 保存用户信息
     * @param user
     * @throws SysException
     */
    public void save(User user) throws SysException;

    /**
     * 更新用户信息
     */
    public void update(User user) throws SysException;

}
