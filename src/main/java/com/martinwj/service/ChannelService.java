package com.martinwj.service;

import com.martinwj.entity.Channel;

import java.util.List;

/**
 * @ClassName: ChannelService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface ChannelService {

    /**
     * 查询频道列表
     */
    public List<Channel> list();

    /**
     * 根据主键查询频道信息
     * @param id 主键
     * @return
     */
    public Channel selectById(String id);
}
