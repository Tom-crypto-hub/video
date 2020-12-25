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
     * 根据视频mediaId查找一条视频（电脑端）
     * @param mediaId
     * @return
     */
    Video selectByMediaId(String mediaId);

    /**
     * 根据媒体主键数组，批量删除视频信息
     * @param mediaIdArr
     */
    int batchDeleteByMediaId(@Param("mediaIdArr") String[] mediaIdArr);

    /**
     * 批量更新视频的状态
     * @param param
     * @return
     */
    int batchUpdate(Map<String, Object> param);
}
