package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Video
 * @Description: TODO 视频播放地址表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Video implements Serializable {
    private String videoId;		// 主键
    private String mediaId;		// 媒体信息主键
    private String num;			// 第几集
    private String title;		// 该集标题
    private String image;		// 该集封面
    private String url;			// 视频播放地址
    private String playerId;	// 播放器id
    private String sort;		// 排序用
    private String status;		// 状态，是否禁用。1代表正常，0代表禁用
    private String remark;		// 视频简介
    private Integer power;		// 视频播放权限值
    private String updateTime;	// 发布时间
    private String viewCount;		// 该视频播放总量
    private String viewCountDay;	// 日播放量
    private String viewCountWeek;	// 周播放量
    private String viewCountMonth;	// 月播放量
    private String viewCountYear;	// 年播放量

    private String biaoti;		// 媒体信息名称
    private String play;		// 播放内容
}
