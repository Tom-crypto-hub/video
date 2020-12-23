package com.martinwj.controller.seo_info;

import com.martinwj.entity.Result;
import com.martinwj.entity.Seo;
import com.martinwj.service.SeoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: SeoInfoAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("seo_info")
public class SeoInfoAction {

    @Autowired
    private SeoService seoService;

    /**
     * 查询seo信息
     */
    @RequestMapping("edit.action")
    public String edit(ModelMap map,
                       @RequestParam(value="type") String type) {

        Seo seo = seoService.selectByType(type);
        map.put("seoInfo", seo);

        if ("index".equals(type)) {
            return "admin/seo_index/edit";
        } else if ("list".equals(type)) {
            return "admin/seo_list/edit";
        } else if ("play".equals(type)) {
            return "admin/seo_play/edit";
        } else if ("profile".equals(type)) {
            return "admin/seo_profile/edit";
        } else {
            return null;
        }
    }

    /**
     * 保存seo信息配置
     * @param seo
     * @return
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(Seo seo) {

        seoService.save(seo);
        return Result.success();
    }

}
