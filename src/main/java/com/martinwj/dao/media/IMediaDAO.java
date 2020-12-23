package com.martinwj.dao.media;

import com.martinwj.entity.Media;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: IMediaDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface IMediaDAO {
    /**
     * 查询媒体列表
     * @param param
     * @return
     */
    List<Media> list(Map<String, Object> param);
}
