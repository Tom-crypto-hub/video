package com.martinwj.controller.web;

import com.martinwj.entity.Result;
import com.martinwj.entity.Web;
import com.martinwj.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: WebAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("web")
public class WebAction {

    @Autowired
    private WebService webService;

    /**
     * 查询站点信息
     */
    @RequestMapping("edit.action")
    public String edit(ModelMap map) {

        Web web = webService.selectWebInfo();
        map.put("webInfo", web);

        return "admin/web_info/edit";
    }

    /**
     * 保存站点信息配置
     * @param web
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(Web web) {

        webService.save(web);
        return Result.success();
    }

}
