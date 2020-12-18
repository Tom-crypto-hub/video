package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Nav
 * @Description: TODO 导航表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@SuppressWarnings("serial")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Nav implements Serializable {
    private String id;		// 主键
    private String name;	// 导航名称
    private String link;	// 导航链接
    private String type;	// 导航类型（system：系统内置；user：用户自定义）
    private String sort;	// 排序用
    private String isIndex;	// 是否首页
    private String isUse;	// 是否可用
    private String channelId;// 频道主键
}
