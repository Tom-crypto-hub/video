package com.martinwj.dao.video;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Video;
import it.sauronsoftware.jave.VideoInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IVideoDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Repository
public interface IVideoDAO {

    /**
     * 查询该视频下的第一集
     * @param mediaId 媒体信息主键
     * @return
     */
    Video selectByMediaIdFirst(String mediaId);

    /**
     * 查询该视频下的最新一集
     * @param mediaId 媒体信息主键
     * @return
     */
    Video selectByMediaIdLast(String mediaId);

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
     * 根据视频主键查询视频播放源
     * @param videoId
     * @return
     */
    Video selectVideoPlayById(String videoId);

    /**
     * 根据视频mediaId查找媒体信息（电脑端）
     * @param mediaId
     * @return
     */
    List<Video> selectByMediaId(@Param("mediaIdArr") String[] mediaIdArr);

    /**
     * 根据媒体主键数组，批量删除视频信息
     * @param mediaIdArr
     */
    int batchDeleteByMediaId(@Param("mediaIdArr") String[] mediaIdArr);

    /**
     * 插入一条新数据
     * @param video
     * @return
     */
    int insert(Video video);

    /**
     * 更新一条数据
     * @param video
     * @return
     */
    int update(Video video);

    /**
     * 更新统一封面
     * @param mediaId 媒体信息主键
     * @param imgUrl 图片地址
     * @return
     */
    int updateImage(@Param("mediaId") String mediaId, @Param("image") String image);

    /**
     * 批量更新视频状态
     * @param videoIdArr 主键数组
     * @param status 状态
     * @return
     */
    int batchUpdateStatus(@Param("videoIdArr") String[] videoIdArr, @Param("status") String status);

    /**
     * 根据媒体主键和第几集，查询是否存在既存数据
     * @param mediaId 媒体主键
     * @param num 第几集
     * @return
     */
    Video selectByMediaIdAndNum(@Param("mediaId") String mediaId, @Param("num") String num);

    /**
     * 批量删除
     * @param videoIdArr 主键数组
     * @return
     */
    int batchDelete(@Param("videoIdArr") String[] videoIdArr);

    /**
     * 更新统一权限值
     * @param mediaId
     * @param power
     */
    int updatePower(@Param("mediaId") String mediaId, @Param("power") String power);

    /**
     * 批量更新视频的状态
     * @param param
     * @return
     */
    int batchUpdate(Map<String, Object> param);

    /**
     * 获取视频信息（前台播放页面专用）
     * @param videoId 视频主键
     * @return
     */
    Video selectByIdWithPortal(String videoId);

    /**
     * 根据媒体信息主键，查询该视频下的所有播放集数，从小到大排序（状态正常的）
     * @param mediaId 媒体信息主键
     * @return
     */
    List<Video> listByMediaId(@Param("mediaId") String mediaId);
}
