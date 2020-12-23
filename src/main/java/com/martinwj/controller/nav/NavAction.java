package com.martinwj.controller.nav;

import com.martinwj.entity.Nav;
import com.martinwj.entity.Result;
import com.martinwj.service.NavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: NavAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("nav_info")
public class NavAction {

    @Autowired
    private NavService navService;

    /**
     * 查询所有导航
     */
    @RequestMapping("list.action")
    public String list(ModelMap map) {

        List<Nav> list = navService.list();
        map.put("list", list);

        return "admin/nav_info/list";
    }

    /**
     * 保存导航
     * @param idArr 导航主键数组
     * @param sortArr 导航排序数组
     * @param nameArr 导航名称数组
     * @param linkArr 导航权限值数组
     * @param isIndexArr
     * @param isUseArr
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(
            @RequestParam(value="idArr") String[] idArr,
            @RequestParam(value="sortArr") String[] sortArr,
            @RequestParam(value="nameArr") String[] nameArr,
            @RequestParam(value="linkArr") String[] linkArr,
            @RequestParam(value="isIndexArr") String[] isIndexArr,
            @RequestParam(value="isUseArr") String[] isUseArr) {

        List<Nav> navList = new ArrayList<Nav>();

        // 遍历idArr数组
        for (int i=0; i<idArr.length; i++) {
            Nav nav = new Nav();
            nav.setId(idArr[i]);
            nav.setSort(sortArr[i]);
            nav.setName(nameArr[i]);
            nav.setLink(linkArr[i]);
            try {
                nav.setIsIndex(isIndexArr[i]);
            } catch (Exception e) {
                nav.setIsIndex("0");
            }
            try {
                nav.setIsUse(isUseArr[i]);
            } catch (Exception e) {
                nav.setIsUse("0");
            }

            navList.add(nav);
        }

        navService.save(navList);

        return Result.success();
    }

    /**
     * 删除
     * @param idArr 导航主键数组
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result delete(ModelMap map,
                         @RequestParam(value="idArr") String[] idArr) {

        navService.delete(idArr);
        return Result.success();
    }

}
