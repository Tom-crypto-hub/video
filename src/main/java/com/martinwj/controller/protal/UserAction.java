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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.internet.ParseException;
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
            return Result.error(ErrorMsg.ERROR_100002.getMsg());
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

    /**
     * 找回密码
     * 邮箱发送
     * @param email 传的邮箱
     * @return
     */
    @RequestMapping("find_pwd_email.json")
    @ResponseBody
    public Result find_pwd_email(String email) throws SysException{
        //验证邮箱是否为空，空的话邮件发不过去，抛异常
        if(StringUtils.isEmpty(email)){
            throw new SysException(ErrorMsg.ERROR_100008);
        }
        //给当前邮箱发送邮件
        userService.findPwdEmail(email);
        return Result.success();
    }

    /**
     * 找回密码
     * 校验验证码
     * @param email 校验邮箱
     * @param identifyingCode 邮箱收到的验证码
     * @return
     * @throws SysException
     */
    @RequestMapping("find_pwd_code.json")
    @ResponseBody
    public Result find_pwd_code(String email,String identifyingCode) throws SysException, ParseException {
        // 校验验证码是否为空
        if(StringUtils.isEmpty(identifyingCode)){
            throw new SysException(ErrorMsg.ERROR_100013);
        }

        //校验验证码是否正确
        userService.findPwdCode(email, identifyingCode);
        return Result.success();
    }

    /**
     * 找回密码
     * 设置新密码
     * @param email 校验邮箱
     * @param identifyingCode 邮箱收到的验证码
     * @Param password 新密码
     * @return
     * @throws SysException
     */
    @RequestMapping("set_new_pass_word.json")
    @ResponseBody
    public Result set_new_pass_word(String email,String identifyingCode, String password) throws SysException, ParseException {
        // 校验密码是否为空
        if(StringUtils.isEmpty(password)){
            throw new SysException(ErrorMsg.ERROR_100006);
        }
        // 2.4 校验密码长度
        password = password.replaceAll("\\s*", "");
        if (password.length() < 6 || password.length() > 16) {
            throw new SysException(ErrorMsg.ERROR_100007);
        }

        // 设置新密码
        userService.setNewPassword(email, identifyingCode, password);

        return Result.success();
    }

    /**
     * 换绑
     * 发邮件
     * @param email
     * @param userToken
     * @return
     * @throws SysException
     */
    @RequestMapping("/send_email.json")
    @ResponseBody
    public Result send_email(String email,String userToken) throws SysException {
        if(StringUtils.isEmpty(email)){
            throw new SysException(ErrorMsg.ERROR_100008);
        }
        User userTemp = new User();
        User user = userService.getUserByUserToken(userToken);
        //如果换绑前的邮箱为空的话，则空的邮箱地址发不过去，会抛异常
        if(user==null){
            throw new SysException(ErrorMsg.ERROR_100008);
        }
        if(user.getEmail().equals(email)){
            return Result.error("新旧邮箱相同！！!给老子换");
        }
        //给当前邮箱发送邮件
        userTemp.setId(user.getId());
        userTemp.setLoginName(user.getLoginName());
        userTemp.setEmail(email);
        userService.updateEmail(userTemp);
        return Result.success();

    }

    /**
     * 换绑
     * 发验证码
     * @param email 新的邮箱地址
     * @param identifyingCode 验证码
     * @param userToken
     * @return
     * @throws SysException
     * @throws ParseException
     */
    @RequestMapping("/change_email.json")
    @ResponseBody
    public Result change_email(String email,String identifyingCode,String userToken) throws SysException, ParseException {
        User user = userService.getUserByUserToken(userToken);
        if(StringUtils.isEmpty(identifyingCode)){
            throw new SysException(ErrorMsg.ERROR_100013);
        }
        //校验验证码是否正确
        userService.updateEmailCode(user,email,identifyingCode);
        return Result.success();
    }

}
