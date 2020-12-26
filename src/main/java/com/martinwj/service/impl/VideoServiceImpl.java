package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.media.IMediaDAO;
import com.martinwj.dao.player.IPlayerDAO;
import com.martinwj.dao.video.IVideoDAO;
import com.martinwj.entity.Media;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import com.martinwj.service.VideoService;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    @Autowired
    private IMediaDAO iMediaDAO;

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
     * 视频播放地址保存
     * @param video
     */
    public void save(Video video) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        // 1.0 如果排序次数没填的话，则默认为1
        if (StringUtils.isEmpty(video.getSort())) {
            video.setSort("1");
        }

        // 2.0 如果播放总量没填的话，则默认为1
        if (StringUtils.isEmpty(video.getViewCount())) {
            video.setViewCount("1");
        }

        // 3.0 保存视频信息
        // 判断是新增还是更新
        if (StringUtils.isEmpty(video.getVideoId())) {
            // 新增
            video.setStatus("1");
            video.setUpdateTime(now);
            video.setViewCountDay("0");
            video.setViewCountWeek("0");
            video.setViewCountMonth("0");
            video.setViewCountYear("0");
            iVideoDAO.insert(video);
        } else {
            // 更新
            iVideoDAO.update(video);
        }

        // 4.0 更新对应的媒体的时间、状态等
        Media media = new Media();
        media.setMediaId(video.getMediaId());
        media.setUpdateTime(currentTime);
        media.setHasVideo(1);

        iMediaDAO.updateMedia(media);
    }

    /**
     * 更新统一封面
     * @param mediaId 媒体信息主键
     * @param image 图片地址
     */
    public void updateImage(String mediaId, String image) {
        iVideoDAO.updateImage(mediaId, image);
    }

    /**
     * 批量更新排序
     * @param videoIdArr 主键数组
     * @param sortArr 排序数组
     */
    public void updateSort(String[] videoIdArr, String[] sortArr) {
        for (int i=0; i<videoIdArr.length; i++) {
            Video video = new Video();
            video.setVideoId(videoIdArr[i]);
            video.setSort(sortArr[i]);

            iVideoDAO.update(video);
        }
    }

    /**
     * 批量更新视频状态
     * @param videoIdArr 主键数组
     * @param status 状态
     */
    public void batchUpdateStatus(String[] videoIdArr, String status) {
        iVideoDAO.batchUpdateStatus(videoIdArr, status);
    }

    /**
     * 批量删除视频
     * @param videoIdArr 主键数组
     * @throws SysException
     */
    public void batchDelete(String[] videoIdArr) throws SysException {
        for (int i=0; i<videoIdArr.length; i++) {
            // 只有已经禁用的视频才可以被删除
            Video video = iVideoDAO.selectById(videoIdArr[i]);
            if ("1".equals(video.getStatus())) {
                throw new SysException(ErrorMsg.ERROR_600005);
            }
        }
        iVideoDAO.batchDelete(videoIdArr);
    }

    /**
     * 更新统一权限值
     * @param mediaId
     * @param power
     */
    public void updatePower(String mediaId, String power) {
        iVideoDAO.updatePower(mediaId, power);
    }

    /**
     * 根据主键查询信息（前台播放页面专用）
     * @param videoId 主键
     * @return
     */
    public Video chooseByIdWithPortal(String videoId) {
        // 获取视频信息（前台播放页面专用）
        Video video = iVideoDAO.selectByIdWithPortal(videoId);
        if (video!=null) {
            // 播放总量自增
            int nViewCount = Integer.parseInt(video.getViewCount());
            nViewCount++;

            // 日播放量自增
            int nViewCountDay = Integer.parseInt(video.getViewCountDay());
            nViewCountDay++;

            // 周播放量自增
            int nViewCountWeek = Integer.parseInt(video.getViewCountWeek());
            nViewCountWeek++;

            // 月播放量自增
            int nViewCountMonth = Integer.parseInt(video.getViewCountMonth());
            nViewCountMonth++;

            // 年播放量自增
            int nViewCountYear = Integer.parseInt(video.getViewCountYear());
            nViewCountYear++;

            // 更新播放次数
            Video video2 = new Video();
            video2.setVideoId(video.getVideoId());
            video2.setViewCount(String.valueOf(nViewCount));
            video2.setViewCountDay(String.valueOf(nViewCountDay));
            video2.setViewCountWeek(String.valueOf(nViewCountWeek));
            video2.setViewCountMonth(String.valueOf(nViewCountMonth));
            video2.setViewCountYear(String.valueOf(nViewCountYear));

            iVideoDAO.update(video2);
        }

        return video;
    }

    /**
     * 根据媒体信息主键，查询该视频下的所有播放集数，从小到大排序（状态正常的）
     * @param mediaId 媒体信息主键
     * @return
     */
    public List<Video> listByMediaId(String mediaId) {
        return iVideoDAO.listByMediaId(mediaId);
    }
}
