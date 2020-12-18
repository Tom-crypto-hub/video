package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Activate
 * @Description: TODO 用户认证激活表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Activate {
    private String id;			// 主键
    private String userId;		// 用户id
    private String type;		// 激活类型
    private String code;		// 验证码
    private String createTime;	// 验证码生成时间
}
