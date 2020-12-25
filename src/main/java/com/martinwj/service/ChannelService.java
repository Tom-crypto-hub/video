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
     * 查询频道栏目列表
     */
    List<Channel> list();

    /**
     * 根据主键查询频道信息
     *
     * @param id
     * @return
     */
    public Channel selectById(String id);

    /**
     * 根据主键删除频道信息
     * @param id
     * @return
     */
    int deleteById(String id);

    /**
     * 添加频道信息
     * @return
     */
    int addList(Channel channel);

    /**
     * 更新频道信息
     * @param channel
     * @return
     */
    int updateList(Channel channel);

    /**
     * 保存频道信息
     * @param channel
     * @return
     */
    int save(Channel channel);
}
