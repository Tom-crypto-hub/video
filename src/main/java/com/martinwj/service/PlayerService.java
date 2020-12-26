package com.martinwj.service;

import com.martinwj.entity.Channel;
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
    /**
     *  添加播放器
     */
    int addList(Player player);

    /**
     * 删除播放器
     */
    int deleteById(String[] id);

    /**
     * 编辑播放器列表
     */
    int updateList(Player player);

    /**
     * 保存
     */
    void batchSave(List<Player> player);

    /**
     * 根据主键查询频道信息
     *
     * @param id
     * @return
     */
    public Player selectById(String id);

    /**
     * 保存播放器
     * @param player
     */
    public int save(Player player);
}
