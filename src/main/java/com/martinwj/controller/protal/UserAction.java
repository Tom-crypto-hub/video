package com.martinwj.controller.protal;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @ClassName: UserAction
 * @Description: TODO 用户登陆注册门户
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Controller
@RequestMapping("portal/user")
public class UserAction {
    private UserService userService;

    /**
     * 用户注册
     * @throws Exception
     */
    @RequestMapping("register.json")
    @ResponseBody
    public Result register(HttpServletRequest request) throws Exception {

//        Map<String, Object> info = userService.register(request);
//
//        return Result.success().add("info", info);
        return null;
    }

    /**
     * 用户注册时，邮箱验证
     * @param request
     * @param userToken 用户凭证
     * @return
     * @throws Exception
     */
    @RequestMapping("register_email.json")
    @ResponseBody
    public Result registerEmail(HttpServletRequest request, @RequestParam(value="userToken") String userToken) throws Exception {


        return null;
    }

    /**
     * 邮箱验证，激活账号
     * @param request
     * @param identifyingCode 验证码
     * @param userToken 用户凭证
     * @return
     * @throws Exception
     */
    @RequestMapping("validate_email.json")
    @ResponseBody
    public Result validateEmail(HttpServletRequest request,
                                @RequestParam(value="identifyingCode") String identifyingCode,
                                @RequestParam(value="userToken") String userToken) throws Exception {
        // 校验验证码

        // 校验用户凭证

        // 取出用户身份信息

        // 激活

        // 将用户信息保存进session

        // 返回
        return Result.success();
    }

    /**
     * 用户登录
     * @throws Exception
     */
    @RequestMapping("login.json")
    @ResponseBody
    public Result login(User user, HttpServletRequest request) throws Exception {

        //Map<String, Object> info = userService.login(request);
        //
        Map<String, Object> info = userService.login(request);
        if(user==null){
            info.put("type","error");
            info.put("msg","未获取到数据");
            return (Result) info;
        }
        // 从session中获取验证码值
        HttpSession session = request.getSession();
        user = userService.selectUser(user.getLoginName(),user.getPassWord());
        if(user==null){
            info.put("type","error");
            info.put("msg","用户名或密码错误");
            return (Result) info;
        }
        session.setAttribute("user",user);
        info.put("type","success");
        info.put("msg","登陆成功");
        return Result.success().add("info", info);
    }

    /**
     * 用户注销，清除session
     */
    @RequestMapping("logout.json")
    @ResponseBody
    public Result login(HttpSession session) {
        // 清除session
        session.invalidate();

        return Result.success();
    }

}
