package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Channel
 * @Description: TODO 频道栏目表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Channel implements Serializable {
    private String id;			// 主键
    private String name;		// 名称
    private String sort;		// 排序用
    private String template;	// 模板文件名
    private String title;		// 标题
    private String keywords;	// 关键字
    private String description;	// 描述
}
