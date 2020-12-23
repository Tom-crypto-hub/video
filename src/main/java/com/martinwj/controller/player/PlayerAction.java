package com.martinwj.controller.player;

import com.martinwj.entity.Player;
import com.martinwj.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @ClassName: PlayerAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("player_info")
public class PlayerAction {

    @Autowired
    private PlayerService playerService;

    /**
     * 查询所有播放器
     */
    @RequestMapping("list.action")
    public String list(ModelMap map) {

        List<Player> list = playerService.list();
        map.put("list", list);

        return "admin/player_info/list";
    }

}
