package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @ClassName: History
 * @Description: TODO 用户历史播放记录
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private String id;			// 主键
    private String videoId;		// 视频主键
    private String userId;		// 用户id
    private Date updateTime;	// 浏览时间
}
