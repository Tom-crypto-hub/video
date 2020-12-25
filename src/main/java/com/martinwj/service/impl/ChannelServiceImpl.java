package com.martinwj.service.impl;

import com.martinwj.dao.channel.IChannelDAO;
import com.martinwj.entity.Channel;
import com.martinwj.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: ChannelServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    private IChannelDAO iChannelDAO;

    /**
     * 查询频道列表
     */
    public List<Channel> list() {
        return iChannelDAO.list();
    }

    /**
     * 根据主键查询频道信息
     * @param id 主键
     * @return
     */
    public Channel selectById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return iChannelDAO.selectById(id);
    }

    /**
     * 通过Id删除频道
     * @param id
     * @return
     */
    @Override
    public int deleteById(String id) {
        return iChannelDAO.deleteById(id);
    }

    /**
     * 添加频道信息
     * @return
     */
    @Override
    public int addList(Channel channel) {
        return iChannelDAO.addList(channel);
    }

    /**
     * 更新频道信息
     * @param channel
     * @return
     */
    @Override
    public int updateList(Channel channel) {
        return iChannelDAO.updateList(channel);
    }

    /**
     * 保存频道信息
     * @param channel
     * @return
     */
    @Override
    public int save(Channel channel) {
        if(StringUtils.isEmpty(channel.getId())){
            return addList(channel);
        } else {
            return updateList(channel);
        }
    }

}
