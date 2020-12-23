package com.martinwj.dao.channel;

import com.martinwj.entity.Channel;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IChannelDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Repository
public interface IChannelDAO {

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
}
