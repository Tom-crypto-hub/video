package com.martinwj.service.impl;

import com.martinwj.dao.channel.IChannelDAO;
import com.martinwj.entity.Channel;
import com.martinwj.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
}
