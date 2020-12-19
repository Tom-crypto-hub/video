package com.martinwj.dao.user;

import com.martinwj.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: IUserDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface IUserDAO {
    /**
     * 查询用户
     *
     * @param loginName 登录名
     * @param passWord  登录密码
     * @return
     */
    User selectUser(@Param("loginName") String loginName, @Param("passWord") String passWord);

    /**
     * 查询用户数量
     * @param loginName 注册时填写的账号
     * @param email 注册时填写的邮箱
     * @return
     */
    int countUser(@Param("loginName") String loginName, @Param("email") String email);

    /**
     * 注册新用户
     * @param user
     */
    int insert(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int update(User user);

    /**
     * 根据邮箱查询用户
     * @param email
     * @return
     */
    User selectUserByEmail(String email);

}
