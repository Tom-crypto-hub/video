package com.martinwj.service.impl;

import com.martinwj.dao.player.IPlayerDAO;
import com.martinwj.entity.Channel;
import com.martinwj.entity.Player;
import com.martinwj.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @ClassName: PlayerServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private IPlayerDAO iPlayerDAO;

    /**
     * 查询播放器列表
     */
    public List<Player> list() {
        return iPlayerDAO.list();
    }

    /**
     * 添加播放器
     * @param player
     * @return
     */
    @Override
    public int addList(Player player) {
        return iPlayerDAO.addList(player);
    }

    /**
     * 删除播放器
     * @param id
     * @return
     */
    @Override
    public int deleteById(String[] id) {
        return iPlayerDAO.deleteById(id);
    }

    /**
     * 更新（编辑）
     * @param player
     * @return
     */
    @Override
    public int updateList(Player player) {
        return iPlayerDAO.updateList(player);
    }

    /**
     * 保存
     * @param player
     * @return
     */
    public int save(Player player) {
        if(StringUtils.isEmpty(player.getId())){
            return addList(player);
        } else {
            return updateList(player);
        }
    }

    @Override
    public Player selectById(String id) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        return iPlayerDAO.selectById(id);
    }


}
