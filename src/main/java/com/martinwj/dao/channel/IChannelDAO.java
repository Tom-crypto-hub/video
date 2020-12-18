package com.martinwj.dao.channel;

import com.martinwj.entity.Channel;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: IChannelDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface IChannelDAO {
    /**
     * 根据主键查询频道信息
     *
     * @param id
     * @return
     */
    public Channel selectById(String id);
}
