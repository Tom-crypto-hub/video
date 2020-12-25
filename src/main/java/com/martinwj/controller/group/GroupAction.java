package com.martinwj.controller.group;/*
 * @ClassName GroupAction
 * @Description TODO
 * @Author oscar
 * @Date 2020/12/25 9:37
 * @Version 1.0
 */

import com.martinwj.entity.Group;
import com.martinwj.entity.Nav;
import com.martinwj.entity.Result;
import com.martinwj.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("group_info")
public class GroupAction {
    @Autowired
    private GroupService groupService;
    /**
     * 查询所有用户组
     */
    @RequestMapping("list.action")
    public String list(ModelMap map) {

        List<Group> list = groupService.list();
        map.put("list", list);

        return "admin/group_info/list";
    }

    /**
     *  保存用户组
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(@RequestParam(value="idArr") String[] idArr,
                       @RequestParam(value="sortArr")String[]sortArr,
                       @RequestParam(value="nameArr")String[]nameArr,
                       @RequestParam(value="powerArr")String[]powerArr)
                       {
        Group group = new Group();
        for(int i = 0;i<idArr.length;i++){
            group.setId(idArr[i]);
            group.setSort(sortArr[i]);
            group.setName(nameArr[i]);
            group.setPower(powerArr[i]);
            groupService.save(group);
        }


        return Result.success();
    }

    /**
     * 删除
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result deleteList(ModelMap map,@RequestParam(value = "idArr")String[] idArr){
        groupService.deleteById(idArr);
        return Result.success();
    }


}
