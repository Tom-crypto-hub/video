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

}
