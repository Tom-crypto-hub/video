package com.martinwj.service;

import com.martinwj.entity.Player;

import java.util.List;

/**
 * @ClassName: PlayerService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
public interface PlayerService {

    /**
     * 查询播放器列表
     */
    public List<Player> list();

}
