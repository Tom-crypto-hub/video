package com.martinwj.controller.player;

import com.martinwj.entity.Channel;
import com.martinwj.entity.Player;
import com.martinwj.entity.Result;
import com.martinwj.exception.SysException;
import com.martinwj.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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

    /**
     * 删除播放器
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result deleteList(@RequestParam(value = "idArr")String[] idArr)  {
        playerService.deleteById(idArr);
        return Result.success() ;
    }

    /**
     * 添加/编辑频道
     */
    @RequestMapping("batch_save.json")
    @ResponseBody
    public Result save(@RequestParam(value = "idArr") String[] idArr,
                       @RequestParam(value = "sortArr") String[] sortArr,
                       @RequestParam(value = "nameArr") String[] nameArr){
        Player player = new Player();
        for(int i=0;i<idArr.length;i++){

            player.setId(idArr[i]);
            player.setSort(sortArr[i]);
            player.setName(nameArr[i]);
            player.setContent("<iframe src=\"http://jx.yylep.com/?url={url}\" width=\"100%\" height=\"100%\" frameborder=\"0\" allowfullscreen=\"true\" allowtransparency=\"true\" style=\"overflow: visible;border: 0;\"></iframe>");
            playerService.save(player);
        }

        return Result.success();
    }

    @RequestMapping("edit.action")
    public String edit(String id, ModelMap map){

        if(!StringUtils.isEmpty(id)){
            Player player = playerService.selectById(id);
            map.put("playerInfo",player);
            map.put("id",id);

        }

        return "admin/player_info/edit";
    }

    /**
     * 编辑后保存
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result saveEdit(ModelMap map, Player player){

        int save = playerService.save(player);
        map.put("save",save);
        return Result.success();
    }
}
