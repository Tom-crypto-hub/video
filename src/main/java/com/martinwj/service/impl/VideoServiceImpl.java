package com.martinwj.service.impl;

import com.martinwj.dao.player.IPlayerDAO;
import com.martinwj.dao.video.IVideoDAO;
import com.martinwj.entity.Video;
import com.martinwj.service.VideoService;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: VideoServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-24
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private IVideoDAO iVideoDAO;
    @Autowired
    private IPlayerDAO iPlayerDAO;

    /**
     * 根据媒体信息主键，查询该视频下所播放的集数，从大到小排序（后台管理专用）
     * @param mediaId 媒体信息主键
     * @return
     */
    @Override
    public List<Video> listByMediaIdDesc(String mediaId) {
        return iVideoDAO.listByMediaIdDesc(mediaId);
    }

    /**
     * 根据主键查询信息
     * @param videoId 主键
     * @return
     */
    public Video selectById(String videoId) {
        return iVideoDAO.selectById(videoId);
    }

    /**
     * 根据视频主键查询视频播放源（电脑端）
     * @param videoId
     * @return
     */
    @Override
    public String selectVideoPlayById(String videoId) {
        // 1.0 获取视频信息
        Video video = iVideoDAO.selectVideoPlayById(videoId);

        // 2.0 获取播放器配置参数
        String content = iPlayerDAO.selectContentById(video.getPlayerId());

        // 3.0 判断视频地址是否存在
        if (StringUtils.isEmpty(content)) {
            content = "";
        } else {
            if (StringUtils.isEmpty(video.getUrl())) {
                // 3.1 不存在时，不返回播放内容
                content = "";
            } else {
                // 3.2 存在时，替换播放器配置中的变量
                content = content.replace("{url}", video.getUrl());
            }
        }

        return content;
    }

    /**
     * 根据视频mediaId查找一条视频（电脑端）
     * @param mediaId
     * @return
     */
    @Override
    public Video selectByMediaId(String mediaId) {
        return iVideoDAO.selectByMediaId(mediaId);
    }
}
