package com.martinwj.controller.channel;

import com.martinwj.entity.Channel;
import com.martinwj.service.ChannelService;
import com.martinwj.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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

    /**
     * 查询所有频道
     */
    @RequestMapping("list.action")
    public String list(ModelMap map) {

        List<Channel> list = channelService.list();
        map.put("list", list);

        return "admin/channel_info/list";
    }

}
