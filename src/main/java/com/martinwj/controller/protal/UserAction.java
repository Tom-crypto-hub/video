package com.martinwj.controller.protal;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.UserService;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.UserService;
import com.martinwj.util.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    /**
     * 用户注册
     * @throws Exception
     */
    @RequestMapping("register.json")
    @ResponseBody
    public Result register(HttpServletRequest request) throws Exception {

        Map<String, Object> info = null ;
        try{
            info = userService.register(request);
        }catch(SysException e){
            return Result.error(e.getMessage());
        }finally{
            return Result.success().add("info",info);
        }

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

        if (StringUtils.isEmpty(userToken)) {
            throw new SysException(ErrorMsg.ERROR_100011);
        }
        User user = userService.getUserByUserToken(userToken);

        userService.sendEmail(user, "注册邮箱验证", "register");

        return Result.success();
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
        if (StringUtils.isEmpty(identifyingCode)) {
            throw new SysException(ErrorMsg.ERROR_100013);
        }

        // 校验用户凭证
        if (StringUtils.isEmpty(userToken)) {
            throw new SysException(ErrorMsg.ERROR_100011);
        }

        // 取出用户身份信息
        User user = userService.getUserByUserToken(userToken);
        System.out.println(user);

        User userTemp = new User();
        userTemp.setId(user.getId());
        // 激活
        userTemp.setStatus("1");

        userService.validateEmail(userTemp, identifyingCode);

        // 将用户信息保存进session
        request.getSession().setAttribute("userInfo", user);
        return Result.success();
    }

    /**
     * 用户登录
     * @throws Exception
     */
    @RequestMapping("login.json")
    @ResponseBody
    public Result login(HttpServletRequest request) throws Exception {

        /**
         *  定义Map，去调ServiceImpl里面的Login方法，去验证用户信息
         *  捕获异常的话，就返回对应的错误信息
         *  未捕获到异常的话，就调用success方法，添加Info
         */
        Map<String, Object> info = null;
        try{
            info = userService.login(request);
        }catch (SysException e ){
            return Result.error(e.getMessage());
        }finally {
            return Result.success().add("info", info);
        }

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
