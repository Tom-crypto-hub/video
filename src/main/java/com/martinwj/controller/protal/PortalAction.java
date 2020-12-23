package com.martinwj.controller.protal;

import com.martinwj.entity.*;
import com.martinwj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName: PortalAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Controller
@RequestMapping("portal")
public class PortalAction {

    @Autowired
    private NavService navService;
    @Autowired
    private ChannelService channelService;
    @Autowired
    private WebService webService;
    @Autowired
    private SeoService seoService;
    @Autowired
    private UserService userService;
    @Autowired
    private TemplateService templateService;


    /**
     * 跳转首页
     *
     * @return
     */
    @RequestMapping("index.action")
    public String index(ModelMap map, HttpServletRequest request) {
        // 站点信息
        Web web = webService.selectWebInfo();
        map.put("webInfo", web);

        // 网站首页seo
        Seo seo = seoService.selectByType("index");
        map.put("seoInfo", seo);

        // 获取用户信息
        User user = userService.getUser(request);
        map.put("userInfo", user);

        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        // 获取可用导航
        List<Nav> navlist = navService.listIsUse();
        map.put("navlist", navlist);

        for (Nav nav : navlist) {
            // 判断是否是首页
            if ("1".equals(nav.getIsIndex())) {
                // 判断该链接是否是系统内置的（或者是频道）
                if ("system".equals(nav.getType())) {
                    // 系统默认首页
                    if ("portal/index.action".equals(nav.getLink())) {
                        map.put("active", nav.getLink());
                        return "portal/pc/template/" + templatePC + "/index";
                    } else {
                        // 频道
                        String channelId = nav.getChannelId();
                        map.put("active", "portal/portal.action?channelId=" + channelId);
                        Channel channel = channelService.selectById(channelId);
                        return "portal/pc/template/" + templatePC + "/channel/" + channel.getTemplate();
                    }
                } else {
                    // 自定义链接
                    map.put("active", nav.getLink());
                    return "redirect:" + nav.getLink();
                }
            }
        }
        return "portal/pc/template/" + templatePC + "/index";
    }

    /**
     * 跳转注册页面
     *
     * @return
     */
    @RequestMapping("register.action")
    public String register(ModelMap map) {
        // 站点信息
        Web web = webService.selectWebInfo();
        map.put("web", web);

        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        // 获取可用导航
        List<Nav> navlist = navService.listIsUse();
        map.put("navlist", navlist);

        return "portal/pc/template/" + templatePC + "/user/register_page";
    }

    /**
     * 打开登录弹出层
     *
     * @return
     */
    @RequestMapping("login.action")
    public String login() {
        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        return "portal/pc/template/" + templatePC + "/user/login";
    }

    /**
     * 跳转登录页面
     *
     * @return
     */
    @RequestMapping("login_page.action")
    public String loginPage(ModelMap map) {
        // 站点信息
        Web web = webService.selectWebInfo();
        map.put("web", web);

        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        return "portal/pc/template/" + templatePC + "/user/login_page";
    }

    /**
     * 跳转到找回密码页面
     *
     * @return
     */
    @RequestMapping("find_pwd.action")
    public String findPwd(ModelMap map) {
        // 站点信息
        Web web = webService.selectWebInfo();
        map.put("web", web);

        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        return "portal/pc/template/" + templatePC + "/user/find_pwd";
    }

    /**
     * 跳转到个人信息页面
     * @param request
     * @param map 用map键值对存储用户数据
     * @return
     */
    @RequestMapping("/accountset.action")
    public String accountSet(HttpServletRequest request,ModelMap map){
        User user = userService.getUser(request);
        map.put("userInfo",user);
        map.put("webInfo",webService.selectWebInfo());
        String templatePC = templateService.selectNameByType("pc");

        return "portal/pc/template/" + templatePC + "/user/personal/accountset";
    }

    /**
     * 跳转频道页面
     * @return
     */
    @RequestMapping("portal.action")
    public String portal(ModelMap map,
                         HttpServletRequest request,
                         @RequestParam(value="channelId") String channelId) {
        // 站点信息
        Web web = webService.selectWebInfo();
        map.put("webInfo", web);

        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        // 判断合法性
        try {
            Integer.parseInt(channelId);
        } catch (Exception e) {
            // 404
            return "portal/pc/template/" + templatePC + "/error/404";
        }
        Channel channel = channelService.selectById(channelId);
        if (channel==null) {
            // 404
            return "portal/pc/template/" + templatePC + "/error/404";
        }
        map.put("channelInfo", channel);

        // 获取用户信息
        User user = userService.getUser(request);
        map.put("userInfo", user);

        // 获取可用导航
        List<Nav> navlist = navService.listIsUse();
        map.put("navlist", navlist);

        map.put("active", "portal.action?channelId="+channelId);

        return "portal/pc/template/" + templatePC + "/channel/" + channel.getTemplate();
    }
}
