package com.martinwj.dao.video;

import com.martinwj.entity.Video;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
