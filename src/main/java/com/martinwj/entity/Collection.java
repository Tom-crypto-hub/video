package com.martinwj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName: Collection
 * @Description: TODO 视频收藏表
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Collection {
    private String id;		// 主键
    private String mediaId;	// 媒体表主键
    private String userId;	// 用户id
}
