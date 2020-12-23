package com.martinwj.entity;

import lombok.*;

import java.io.Serializable;

/**
 * @ClassName: Api
 * @Description: TODO 接口设置类
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api implements Serializable {
    private String id;		// 主键
    private String name;	// 名称，用于让用户识别自己的接口
    private String sort;	// 排序
    private String type;	// 接口类型（方便管理）
    private String typeId;	// 数据来源于哪个分类
    private String field;	// 该接口定义了哪些字段
    private String num;		// 返回多少条数据
    private String tag;		// 该接口定义了哪些标签
    private String rankType;	// 排行榜类型（day：日榜；week：周榜；month：月榜；year：年榜）
    private String selectVideo;	// 是否查询该媒体下的视频信息（0：不查询；1：第一集；2：最新一集）
    private String cacheTime;	// 缓存时间

}
