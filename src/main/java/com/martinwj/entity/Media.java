package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: Media
 * @Description: TODO 媒体信息表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Media implements Serializable {
    private String mediaId;	// 媒体信息主键
    private String typeId;	// 所属分类
    private String status;	// 状态，是否已删除至回收站。1代表正常
    private String haibao;	// 海报
    private String dafengmian;// 大封面
    private String fengmian;// 小封面
    private String biaoti;	// 标题
    private String bieming;	// 别名
    private String jianjie;	// 简介
    private String tag;		// 标签
    private Date updateTime;
    private int hasVideo;	// 判断该媒体下是否有视频（1：有；0：没有）
}
