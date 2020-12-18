package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Template
 * @Description: TODO 模板配置表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("serial")
public class Template implements Serializable {
    private String id;		// 主键
    private String type;	// 类型（pc：电脑端； m：手机端）
    private String name;	// 模板的名称
}
