package com.martinwj.controller.media;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.entity.Media;
import com.martinwj.entity.Type;
import com.martinwj.service.MediaService;
import com.martinwj.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: MediaAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("media")
public class MediaAction {

    @Autowired
    private TypeService typeService;
    @Autowired
    private MediaService mediaService;

    /**
     * 查询所有媒体信息（正常）
     * @param map
     * @param keyWord 搜索关键字
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("list_normal.action")
    public String listNormal(ModelMap map,
                             @RequestParam(required=false, value="typeId") String typeId,
                             @RequestParam(required=false, value="keyWord") String keyWord,
                             @RequestParam(value="pageNum", defaultValue="1") int pageNum,
                             @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        // 查询分类信息
        List<Type> typeList = typeService.list();
        map.put("typeList", typeList);

        Map<String, Object> param = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(keyWord)) {
            param.put("keyWord", keyWord.trim());
            map.put("keyWord", keyWord);
        }
        param.put("typeId", typeId);
        map.put("typeId", typeId);

        param.put("status", "1");

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<Media> list = mediaService.list(param);
        PageInfo<Media> pageInfo = new PageInfo<Media>(list);
        map.put("pageInfo", pageInfo);

        return "admin/media_info/list_normal";
    }

}
