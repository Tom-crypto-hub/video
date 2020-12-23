package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.api.IApiDAO;
import com.martinwj.dao.media.IMediaDAO;
import com.martinwj.dao.video.IVideoDAO;
import com.martinwj.entity.Api;
import com.martinwj.entity.Video;
import com.martinwj.exception.SysException;
import com.martinwj.service.ApiService;
import it.sauronsoftware.jave.VideoInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Service
public class ApiServiceImpl implements ApiService {

    @Autowired
    private IApiDAO iApiDAO;

    /**
     * 查询指定类型的接口列表
     * @param type 接口类型
     * @return
     */
    public List<Api> listByType(String type) {
        return iApiDAO.listByType(type);
    }


}
