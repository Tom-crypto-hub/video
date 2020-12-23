package com.martinwj.service.impl;

import com.martinwj.dao.media.IMediaDAO;
import com.martinwj.entity.Media;
import com.martinwj.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MediaServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Service
public class MediaServiceImpl implements MediaService {

    @Autowired
    private IMediaDAO iMediaDAO;

    /**
     * 查询媒体列表
     * @param param
     * @return
     */
    public List<Media> list(Map<String, Object> param) {
        return iMediaDAO.list(param);
    }

}
