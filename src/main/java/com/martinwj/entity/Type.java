package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Type
 * @Description: TODO  分类表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private String id;		// 主键
    private String name;	// 分类名称（电影、电视剧、动漫...）
    private String sort;	// 排序
    private String profileTemplate;	// 详情页模板
    private String playTemplate;	// 播放页模板
}
