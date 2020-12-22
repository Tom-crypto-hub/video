package com.martinwj.controller.admin;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.UserService;
import com.martinwj.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: AdminAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-22
 */
@Controller
@RequestMapping("admin")
public class AdminAction {
    @Autowired
    private UserService userService;

    // 登录页面显示
    @RequestMapping("login.action")
    public String login() {
        return "admin/login";
    }

    /**
     * 管理员登录后台
     * @throws Exception
     */
    @RequestMapping("login.json")
    @ResponseBody
    public Result login(HttpServletRequest request) throws Exception {

        // 1.0 获取登录参数
        String szLoginName = request.getParameter("login_name");
        String szPassWord = request.getParameter("pass_word");

        // 2.0 校验用户
        // 2.1 校验用户名或密码是否填写
        if (StringUtils.isEmpty(szLoginName) || StringUtils.isEmpty(szPassWord)) {
            throw new SysException(ErrorMsg.ERROR_100001);
        }

        // 2.2 校验用户名、密码是否正确
        User user = userService.selectUser(szLoginName, MD5.md5(szPassWord));
        if (user==null) {
            throw new SysException(ErrorMsg.ERROR_100002);
        }

        // 2.3 校验是否是管理员
        if (!"管理员".equals(user.getGroupName())) {
            throw new SysException(ErrorMsg.ERROR_100002);
        }

        // 3.0 校验成功，设置session
        request.getSession().setAttribute("userInfo", user);

        return Result.success();
    }

    /**
     * 登出，清除session
     */
    @RequestMapping("logout.action")
    public String logout(HttpSession session) {
        // 清除session
        session.invalidate();

        return "redirect:login.action";
    }

    /**
     * 获取当前登录的管理员信息
     * @return
     */
    @RequestMapping("get_admin.json")
    @ResponseBody
    public Result getAdmin(HttpServletRequest request) {

        // 判断session
        HttpSession session  = request.getSession();
        // 从session中取出用户身份信息
        User user = (User)session.getAttribute("userInfo");

        return Result.success().add("userInfo", user);
    }

    /**
     * 管理中心首页
     */
    @RequestMapping("center.action")
    public String center(ModelMap map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>程序版本：</p></div>");
        sb.append("	<div class='right'>martin 1.0.0</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>作者：</p></div>");
        sb.append("	<div class='right'>王靖、李佳澳</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>联系方式：</p></div>");
        sb.append("	<div class='right'>QQ：XXXXXXXXXX</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>官网：</p></div>");
        sb.append("	<div class='right'><a href='https://www.martinwj.com/' target='_blank'>https://www.martinwj.com/</a></div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>版权所有：</p></div>");
        sb.append("	<div class='right'>王靖、李佳澳</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");

        map.put("info", sb.toString());

        return "admin/index";
    }

    /**
     * 收费服务
     */
    @RequestMapping("service.action")
    public String service(ModelMap map) {
        StringBuffer sb = new StringBuffer();
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>功能定制：</p></div>");
        sb.append("	<div class='right'>1000起</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>模板定制：</p></div>");
        sb.append("	<div class='right'>5000起/套，500起/单页</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>网站定制：</p></div>");
        sb.append("	<div class='right'>接受任何类型的项目定制，价格详谈</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");
        sb.append("<div class='unit'>");
        sb.append("	<div class='left'><p class='subtitle'>联系方式：</p></div>");
        sb.append("	<div class='right'>王靖，QQ：XXXXXXXX————李佳澳：QQ：XXXXXXXX</div>");
        sb.append("	<span class='clearfix'></span>");
        sb.append("</div>");

        map.put("info", sb.toString());

        return "admin/service/service";
    }

    /**
     * 首页
     */
    @RequestMapping("index.action")
    public String index() {
        return "admin/menu/index";
    }

    /**
     * 全局
     */
    @RequestMapping("basic.action")
    public String basic() {
        return "admin/menu/basic";
    }

    /**
     * 界面
     */
    @RequestMapping("layout.action")
    public String layout() {
        return "admin/menu/layout";
    }

    /**
     * 分类
     */
    @RequestMapping("type.action")
    public String type() {
        return "admin/menu/type";
    }

    /**
     * 用户
     */
    @RequestMapping("user.action")
    public String user() {
        return "admin/menu/user";
    }

    /**
     * 内容
     */
    @RequestMapping("content.action")
    public String content() {
        return "admin/menu/content";
    }

    /**
     * 接口
     */
    @RequestMapping("api.action")
    public String api() {
        return "admin/menu/api";
    }

    /**
     * 站长
     */
    @RequestMapping("founder.action")
    public String founder() {
        return "admin/menu/founder";
    }
}
