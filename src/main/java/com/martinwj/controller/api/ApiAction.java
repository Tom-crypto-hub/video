package com.martinwj.controller.api;

import com.martinwj.entity.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ApiAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Controller
@RequestMapping("api")
public class ApiAction {
    /**
     * 幻灯片数据
     * @param apiId 接口表主键
     * @return
     */
    @RequestMapping("slide.json")
    @ResponseBody
    public Result slide(
            HttpServletRequest request,
            @RequestParam(value="apiId") String apiId) {

//        List<Map<String, Object>> list = slideProfileService.mapListByApiId(apiId);

//        return Result.success().add("list", list);
        return null;
    }
}