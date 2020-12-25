package com.martinwj.controller.channel;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.template.ITemplateDAO;
import com.martinwj.entity.Channel;
import com.martinwj.entity.Result;
import com.martinwj.entity.Template;
import com.martinwj.exception.SysException;
import com.martinwj.service.ChannelService;
import com.martinwj.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: ChannelAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-23
 */
@Controller
@RequestMapping("channel_info")
public class ChannelAction {

    @Autowired
    private ChannelService channelService;
    @Autowired
    private TemplateService templateService;

    /**
     * 查询所有频道
     */
    @RequestMapping("list.action")
    public String list(ModelMap map) {

        List<Channel> list = channelService.list();
        map.put("list", list);

        return "admin/channel_info/list";
    }

    /**
     * 添加/编辑频道
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(ModelMap map, Channel channel){

        int save = channelService.save(channel);
        map.put("",save);
        return Result.success();
    }

    /**
     * 跳转到编辑页面
     * @param id
     * @param map
     * @param request
     * @return
     */
    @RequestMapping("edit.action")
    public String edit(String id, ModelMap map, HttpServletRequest request){
        if(!StringUtils.isEmpty(id)){
            Channel channel = channelService.selectById(id);
            map.put("channelInfo",channel);
            map.put("id",id);

        }
        String type = templateService.selectNameByType("pc");

        String path = request.getSession().getServletContext().getRealPath("WEB-INF/pages/portal/pc/template/" + type + "/channel");
        Map<String, String> template = null;
        List<Map<String, String>> templateList = new ArrayList<>();
        File file = new File(path);
        File[] array = file.listFiles();
        for(int i=0;i<array.length;i++){
            template = new HashMap<String, String>();
            String filename = array[i].getName().replace(".jsp", "");
            template.put("value", filename);
            template.put("name", filename);
            templateList.add(template);
        }
        map.put("templateList", templateList);
        return "admin/channel_info/edit";
    }

    /**
     * 删除频道
     */
    @RequestMapping("delete.json")
    @ResponseBody
    public Result deleteList(ModelMap map, String id) throws SysException {
        if(StringUtils.isEmpty(id)){
            throw new SysException("id为空，删不了");
        }
        int i = channelService.deleteById(id);
        if(i == 0){
            throw new SysException("删除失败");
        }
        return Result.success() ;
    }


}
