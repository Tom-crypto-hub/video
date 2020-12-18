package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Player
 * @Description: TODO 播放器接口表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player {
    private String id;		// 主键
    private String name;	// 名称
    private String content;	// 播放器内容
    private String sort;	// 排序
}
