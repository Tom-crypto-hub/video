package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Slide
 * @Description: TODO 幻灯片数据表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Slide implements Serializable {
    private String id;		// 主键
    private String apiId;	// 接口表的主键
    private String title;	// 标题
    private String summary;	// 看点
    private String image;	// 大图
    private String thumbnail;// 小图
    private String url;		// 链接地址
    private String sort;	// 排序

}
