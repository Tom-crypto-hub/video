package com.martinwj.dao.player;

import com.martinwj.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IPlayerDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Repository
public interface IPlayerDAO {
    
    /**
     * 查询播放器列表
     */
    List<Player> list();

    /**
     *  添加播放器
     */
    int addList(Player player);

    /**
     * 删除播放器
     */
    int deleteById(String[] idArr);

    /**
     * 编辑播放器列表
     */
    int updateList(Player player);

    /**
     * 保存
     */
    int save(Player player);

    /**
     * 根据主键查询频道信息
     *
     * @param id
     * @return
     */
    public Player selectById(String id);
}
