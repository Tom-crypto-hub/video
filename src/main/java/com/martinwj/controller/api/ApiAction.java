package com.martinwj.controller.api;

import com.martinwj.entity.Result;
import com.martinwj.exception.SysException;
import com.martinwj.service.MediaService;
import com.martinwj.service.SlideProfileService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SlideProfileService slideProfileService;
    @Autowired
    private MediaService mediaService;

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

        List<Map<String, Object>> list = slideProfileService.mapListByApiId(apiId);

        return Result.success().add("list", list);
    }

    /**
     * 根据接口自定义查询数据
     * @return
     * @throws SysException
     */
    @RequestMapping("data.json")
    @ResponseBody
    public Result data(
            @RequestParam(value="apiId") String apiId) throws SysException {

        List<Map<String, Object>> list = mediaService.getDataByApiId(apiId);

        return Result.success().add("list", list);
    }
}
