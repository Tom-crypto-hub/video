package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Star
 * @Description: TODO 明星信息表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Star {
    private String id;				// 主键
    private String name;			// 姓名
    private String alias;			// 别名（英文名）
    private String sex;				// 性别
    private String region;			// 地区
    private String blood;			// 血型
    private String birthday;		// 生日
    private String constellation;	// 星座
    private String occupation;		// 职业
    private String image;			// 照片
    private String introduction;	// 简介
}
