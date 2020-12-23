package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: FieldProfile
 * @Description: TODO 字段简况表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldProfile implements Serializable {
    private String id;		// 主键
    private String fieldId;	// 字段的主键
    private String name;	// 字段内容名称（大陆、香港、欧美...）
    private String sort;	// 排序
}
