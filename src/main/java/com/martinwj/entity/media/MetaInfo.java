package com.martinwj.entity.media;

import lombok.Data;

/**
 * @ClassName: MetaInfo
 * @Description: TODO 多媒体数据（包含图片，视频）的基本信息类
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Data
public class MetaInfo {
    /**
     * 多媒体信息的宽度，图片代表宽度，视频代表帧宽度 ，单位为px
     */
    private Integer width;
    /**
     * 多媒体信息的高度，图片代表高度，视频代表帧高度 ，单位为px
     */
    private Integer height;
    /**
     * 多媒体的大小，指的是存储体积，单位为B
     */
    private Long size;
    /**
     * 格式
     */
    private String format;
}
