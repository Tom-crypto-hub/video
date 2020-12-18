package com.martinwj.service;

import com.martinwj.entity.Channel;

/**
 * @ClassName: ChannelService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface ChannelService {
    /**
     * 根据主键查询频道信息
     * @param id 主键
     * @return
     */
    public Channel selectById(String id);
}
