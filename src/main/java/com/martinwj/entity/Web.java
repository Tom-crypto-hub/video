package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Web
 * @Description: TODO 网站信息表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Web implements Serializable {
    private String id;				// 主键
    private String name;			// 站点名称，将显示在浏览器窗口标题等位置
    private String domain;			// 网站 URL
    private String email;			// 管理员 E-mail
    private String recordNumber;	// 网站备案信息代码
    private String statisticalCode;	// 网站第三方统计代码
}
