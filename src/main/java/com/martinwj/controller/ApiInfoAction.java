package com.martinwj.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.entity.Api;
import com.martinwj.service.ApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName: ApiInfoAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Controller
@RequestMapping("api_info")
public class ApiInfoAction {

    @Autowired
    private ApiService apiService;

    /**
     * 查询所有幻灯片接口
     */
    @RequestMapping("list_slide.action")
    public String listSlide(ModelMap map) {

        List<Api> list = apiService.listByType("slide");
        map.put("list", list);

        return "admin/api_info/list_slide";
    }

    /**
     * 查询所有排行榜接口
     */
    @RequestMapping("list_rank.action")
    public String listRank(ModelMap map,
                           @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                           @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<Api> list = apiService.listByType("rank");
        PageInfo<Api> pageInfo = new PageInfo<Api>(list);
        map.put("pageInfo", pageInfo);

        return "admin/api_info/list_rank";
    }
}
