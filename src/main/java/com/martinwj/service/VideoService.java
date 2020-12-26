package com.martinwj.service;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import it.sauronsoftware.jave.VideoInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
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
     *
     * @param mediaId 媒体信息主键
     * @return
     */
    List<Video> listByMediaIdDesc(String mediaId);

    /**
     * 根据主键查询信息
     *
     * @param videoId 主键
     * @return
     */
    Video selectById(String videoId);

    /**
     * 根据视频主键查询视频播放源（电脑端）
     *
     * @param videoId
     * @return
     */
    String selectVideoPlayById(String videoId);

    /**
     * 视频播放地址保存
     *
     * @param Video
     */
    void save(Video video);

    /**
     * 更新统一封面
     *
     * @param mediaId 媒体信息主键
     * @param image   图片地址
     */
    void updateImage(String mediaId, String image);

    /**
     * 批量更新排序
     *
     * @param videoIdArr 主键数组
     * @param sortArr    排序数组
     */
    void updateSort(String[] videoIdArr, String[] sortArr);

    /**
     * 批量更新视频状态
     *
     * @param videoIdArr 主键数组
     * @param status     状态
     */
    void batchUpdateStatus(String[] videoIdArr, String status);

    /**
     * 批量删除视频
     *
     * @param videoIdArr 主键数组
     * @throws SysException
     */
    void batchDelete(String[] videoIdArr) throws SysException;

    /**
     * 更新统一权限值
     *
     * @param mediaId
     * @param power
     */
    void updatePower(String mediaId, String power);

    /**
     * 根据主键查询信息（前台播放页面专用）
     * @param videoId 主键
     * @return
     */
    Video chooseByIdWithPortal(String videoId);

    /**
     * 根据媒体信息主键，查询该视频下的所有播放集数，从小到大排序（状态正常的）
     * @param mediaId 媒体信息主键
     * @return
     */
    public List<Video> listByMediaId(String mediaId);
}

