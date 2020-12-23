package com.martinwj.service;

import com.martinwj.entity.Media;
import com.martinwj.exception.SysException;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: MediaService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface MediaService {

    /**
     * 查询媒体列表
     * @param param
     * @return
     */
    public List<Media> list(Map<String, Object> param);

    /**
     * 根据接口自定义查询数据
     * @param apiId 接口表主键
     * @return
     * @throws SysException
     */
    public List<Map<String, Object>> getDataByApiId(String apiId) throws SysException;

}
