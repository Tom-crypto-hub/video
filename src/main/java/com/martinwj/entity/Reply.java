package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Reply
 * @Description: TODO 对某条评论或回复的回复表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reply implements Serializable {
    private String id;				// 主键
    private String videoId;			// 视频id
    private String commentId;		// 评论表的主键
    private String userId;			// 回复人的id
    private String loginName;		// 回复人的登录名
    private String toUserId;		// 被回复人的id
    private String toLoginName;		// 被回复人的登录名
    private String content;			// 回复内容
    private String updateTime;		// 回复时间
    private String avatar;		    // 用户头像
}
