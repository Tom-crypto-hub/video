package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Comment
 * @Description: TODO 用户对某个视频的评论表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
    private String id;			// 主键
    private String videoId;		// 视频id
    private String userId;		// 用户id
    private String loginName;	// 用户登录名
    private String content;		// 评论内容
    private String updateTime;	// 评论时间

    private String avatar;		// 用户头像
    private List<Reply> replyList;	// 回复列表
}
