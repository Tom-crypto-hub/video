package com.martinwj.service;

import com.martinwj.entity.Video;
import it.sauronsoftware.jave.VideoInfo;

import java.util.List;

/**
 * @ClassName: VideoService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
public interface VideoService {

    /**
     * 根据媒体信息主键，查询该视频下所播放的集数，从大到小排序（后台管理专用）
     * @param mediaId 媒体信息主键
     * @return
     */
    List<Video> listByMediaIdDesc(String mediaId);

    /**
     * 根据主键查询信息
     * @param videoId 主键
     * @return
     */
    Video selectById(String videoId);

    /**
     * 根据视频主键查询视频播放源（电脑端）
     * @param videoId
     * @return
     */
    String selectVideoPlayById(String videoId);

    /**
     * 根据视频mediaId查找一条视频（电脑端）
     * @param mediaId
     * @return
     */
    Video selectByMediaId(String mediaId);

}
