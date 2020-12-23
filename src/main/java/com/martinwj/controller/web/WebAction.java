package com.martinwj.controller.web;

import com.martinwj.entity.Web;
import com.martinwj.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

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

}
