package com.martinwj.controller.user_info;/*
 * @Author oscar
 * @Date 2020/12/25 14:32
 * @Version 1.0
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.constant.FixedOption;
import com.martinwj.entity.Field;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.service.UserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/user_info")
public class UserInfoAction {

    @Autowired
    private UserInfoService userInfoService;

    /**
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @param groupId
     * @param keyWord
     * @return
     */
    @RequestMapping("list_ban.action")
    public String listBan(ModelMap map,@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                          @RequestParam(value="pageSize", defaultValue="10") int pageSize,
                          @RequestParam(value="groupId",required = false) String groupId,
                          @RequestParam(value="keyWord",required = false)  String keyWord){
        // pageHelper分页插
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<User> listBan = userInfoService.listBan(groupId,keyWord);
        PageInfo<User> pageInfo = new PageInfo<>(listBan);

        map.put("pageInfo",pageInfo);
        return "admin/user_info/list_ban";
    }

    /**
     *
     * @param map
     * @param pageNum
     * @param pageSize
     * @param groupId
     * @param keyWord
     * @return
     */
    @RequestMapping("list_normal.action")
    public String listNormal(ModelMap map,@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                          @RequestParam(value="pageSize", defaultValue="5") int pageSize,
                          @RequestParam(value="groupId",required = false) String groupId,
                          @RequestParam(value="keyWord",required = false)  String keyWord){
        // pageHelper分页插
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<User> listNormal = userInfoService.listNormal(groupId,keyWord);
        PageInfo<User> pageInfo = new PageInfo<>(listNormal);

        map.put("pageInfo",pageInfo);
        return "admin/user_info/list_normal";
    }

    @RequestMapping("list_not_active.action")
    public String listNotActive(ModelMap map,@RequestParam(value="pageNum", defaultValue="1") int pageNum,
                             @RequestParam(value="pageSize", defaultValue="5") int pageSize,
                             @RequestParam(value="groupId",required = false) String groupId,
                             @RequestParam(value="keyWord",required = false)  String keyWord){
        // pageHelper分页插
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<User> listNotActive = userInfoService.listNotActive(groupId,keyWord);
        PageInfo<User> pageInfo = new PageInfo<>(listNotActive);

        map.put("pageInfo",pageInfo);
        return "admin/user_info/list_not_active";
    }

    /**
     * 未激活用户
     * 批量激活
     */
    @RequestMapping("batch_update_status.json")
    @ResponseBody
    public Result batchUpdateStatus(@RequestParam("idArr") String[] idArr, @RequestParam("status") String status){
        userInfoService.update(idArr,status);
        return Result.success();
    }

    /**
     *
     */
    @RequestMapping("batch_change_group.json")
    @ResponseBody
    public Result batchChangeGroup(@RequestParam("idArr") String[] idArr,@RequestParam("groupId")String groupId){

        return Result.success();
    }

}
