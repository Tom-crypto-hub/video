package com.martinwj.controller.comment;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Comment;
import com.martinwj.entity.Result;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.CommentService;
import com.martinwj.service.UserInfoService;
import com.martinwj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName: CommentAction
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-26
 */
@Controller
@RequestMapping("/portal/comment_info")
public class CommentAction {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    
    /**
     * 保存用户评论
     * @param videoId 视频主键
     * @param userToken 用户凭证
     * @param content 评论内容
     * @return
     * @throws SysException
     */
    @RequestMapping("save.json")
    @ResponseBody
    public Result save(
            HttpServletRequest request,
            @RequestParam(value="videoId") String videoId,
            @RequestParam(value="userToken") String userToken,
            @RequestParam(value="content") String content) throws SysException {

        // 校验用户是否登录
        if (StringUtils.isEmpty(userToken)) {
            throw new SysException(ErrorMsg.ERROR_100012);
        }

        // 判断session
        HttpSession session  = request.getSession();
        // 从session中取出用户身份信息
        User user = (User)session.getAttribute("user");

        if (user==null) {
            user = userService.getUserInfoByUserToken(userToken);
            // 将用户信息保存进session
            request.getSession().setAttribute("user", user);
        }

        commentService.save(videoId, content, user);

        return Result.success();
    }

    /**
     * 获取某个视频的评论列表
     * @param videoId 视频主键
     * @return
     */
    @RequestMapping("get_comment_list.json")
    @ResponseBody
    public Result getCommentList(
            @RequestParam(value="videoId") String videoId,
            @RequestParam(value="pageNum", defaultValue="1") int pageNum,
            @RequestParam(value="pageSize", defaultValue="10") int pageSize) {

        // pageHelper分页插件
        // 只需要在查询之前调用，传入当前页码，以及每一页显示多少条
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentService.listByVideoId(videoId);
        PageInfo<Comment> pageInfo = new PageInfo<Comment>(list);

        return Result.success().add("pageInfo", pageInfo);
    }

    /**
     * 获取某个视频的评论条数
     * @param videoId 视频主键
     * @return
     */
    @RequestMapping("get_comment_count.json")
    @ResponseBody
    public Result getCommentCount(@RequestParam(value="videoId") String videoId) {

        int count = commentService.getCommentCount(videoId);

        return Result.success().add("count", count);
    }

}
