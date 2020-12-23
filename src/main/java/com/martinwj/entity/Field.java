package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName: Field
 * @Description: TODO 字段信息表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Field implements Serializable {
    private String id;			// 主键
    private String name;		// 分类名称（视频所属分类：电影、电视剧、动漫...）
    private String type;		// 该用户组是否为系统内置（system代表内置的，不可修改）
    private String sort;		// 排序
    private String inputType;	// 填写类型
    private String varName;		// 变量名
    private String content;		// 字段模板
    private String isAllowEdit;	// 该字段是否允许编辑（1：有详情，可以编辑）
    private List<FieldProfile> fieldProfileList;	// 字段详情
    private String isSelected;	// 判断在指定分类中是否已包含此字段（1：包含）
}
