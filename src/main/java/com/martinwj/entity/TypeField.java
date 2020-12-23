package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: TypeField
 * @Description: TODO分类信息字段配置表，某个分类信息中，包含哪些字段
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeField implements Serializable {
    private String id;			// 主键
    private String typeId;		// 分类信息主键
    private String fieldId;		// 字段主键
    private String isScreen;	// 该字段是否允许在列表页进行筛选（1：允许；0：禁止）
    private String isRequired;	// 该字段在编辑页面是否必填（1：必填；0：不必填）
    private String sort;		// 排序

    private String fieldName;	// 字段名称
}
