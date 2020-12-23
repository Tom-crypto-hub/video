package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Email
 * @Description: TODO 邮件设置表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email implements Serializable {
    private String id;		// 主键
    private String smtp;	// SMTP 服务器
    private String port;	// 端口
    private String email;	// 发信人邮件地址
    private String password;// SMTP 身份验证密码
}
