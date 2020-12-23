package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName: Group
 * @Description: TODO 用户组表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group implements Serializable {
    private String id;		// 主键
    private String name;	// 用户组名称
    private String power;	// 用户组权限值，管理员最大（255）
    private String type;	// 该用户组是否为系统内置（system代表内置的，不可修改）
    private String sort;	// 排序
}
