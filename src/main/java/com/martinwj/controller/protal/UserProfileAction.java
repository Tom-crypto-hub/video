package com.martinwj.controller.protal;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Qny;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.entity.UserProfile;
import com.martinwj.exception.SysException;
import com.martinwj.service.TemplateService;
import com.martinwj.service.QnyService;
import com.martinwj.service.UserProfileService;
import com.martinwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @ClassName: UserProfileAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-21
 */
@Controller
@RequestMapping("portal/user_profile")
public class UserProfileAction {
    @Autowired
    private UserService userService;
    @Autowired
    private TemplateService templateService;
    @Autowired
    private UserProfileService userProfileService;
    @Autowired
    private QnyService qnyService;


    /**
     * 跳转修改头像页面
     * @return
     */
    @RequestMapping("change_avatar.action")
    public String changeAvatar() {
        // 获取所选模板
        String templatePC = templateService.selectNameByType("pc");

        return "portal/pc/template/" + templatePC + "/user/personal/avatar";
    }

    /**
     * 保存用户头像
     * @param request
     * @param avatar
     * @param userToken
     * @return
     * @throws IOException
     */
    @RequestMapping("save_avatar.json")
    @ResponseBody
    public Result saveAvatar(
            HttpServletRequest request,
            @RequestParam(value="avatar") String avatar,
            @RequestParam(value="userToken") String userToken) throws IOException, SysException {

        if (StringUtils.isEmpty(userToken)) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        // 判断session
        HttpSession session  = request.getSession();
        // 从session中取出用户身份信息
        User user = (User)session.getAttribute("userInfo");

        if (user==null) {
            user = userService.getUserInfoByUserToken(userToken);
            // 将用户信息保存进session
            request.getSession().setAttribute("user", user);
        }

        // 将base64头像上传到七牛云
        Qny qny = qnyService.selectByType("touxiang");
        avatar = qnyService.uploadAvatar(avatar, qny);
        System.out.println(avatar);

        // 保存用户头像
        UserProfile userProfile = new UserProfile();
        userProfile.setAvatar(avatar);
        userProfile.setUserId(user.getId());

        // 调用service执行 保存用户操作
        userProfileService.save(userProfile);

        // 重新设置session
        user = userService.getUserInfoByUserToken(userToken);
        request.getSession().setAttribute("user", user);

        return Result.success();
    }

}
