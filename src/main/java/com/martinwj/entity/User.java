package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: User
 * @Description: TODO 用户登录注册表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String id;				// 主键
    private String loginName;		// 登录名
    private String passWord;		// 登录密码
    private String email;			// 注册邮箱
    private String registerTime;	// 注册时间
    private String registerIp;		// 注册ip
    private String lastLoginTime;	// 上一次登陆时间
    private String lastLoginIp;		// 上一次登陆ip
    private String status;			// 用户状态。（0：未激活；1：正常；2：封禁）

    private String avatar;			// 用户头像
    private String signPersonal;	// 用户个性签名
    private int point;				// 用户积分
    private String groupId;			// 用户所在用户组id
    private String groupName;		// 用户所在用户组
    private String power;			// 用户的权限值
}
