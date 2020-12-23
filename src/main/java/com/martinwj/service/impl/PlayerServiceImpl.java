package com.martinwj.service.impl;

import com.martinwj.dao.player.IPlayerDAO;
import com.martinwj.entity.Player;
import com.martinwj.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
