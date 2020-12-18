package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Seo
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Seo implements Serializable {
    private String id;			// 主键
    private String title;		// 标题
    private String keywords;	// 关键字
    private String description;	// 描述
    private String type;		// 类型（首页、播放页...）

}
