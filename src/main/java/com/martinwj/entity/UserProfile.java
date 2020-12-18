package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: UserProfile
 * @Description: TODO 用户个人信息简况表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    private String id;			// 主键
    private String userId;		// 用户id
    private String groupId;		// 用户组id
    private String avatar;		// 用户头像图片地址
    private String signPersonal;// 用户个性签名
    private int point;			// 用户积分
}
