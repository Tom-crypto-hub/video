package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.comment.ICommentDAO;
import com.martinwj.dao.reply.IReplyDAO;
import com.martinwj.entity.Comment;
import com.martinwj.entity.Reply;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import com.martinwj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CommentServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-26
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ICommentDAO iCommentDAO;
    @Autowired
    private IReplyDAO iReplyDAO;

    /**
     * 保存用户评论
     * @param videoId
     * @param content
     * @param user
     * @throws SysException
     */
    public void save(String videoId, String content, User user) throws SysException {

        // 1.0 校验评论内容
        if (StringUtils.isEmpty(content)) {
            throw new SysException(ErrorMsg.ERROR_900001);
        }

        // 删除普通标签
        content = content.replaceAll("<(S*?)[^>]*>.*?|<.*? />", "");
        // 删除转义字符
        content = content.replaceAll("&.{2,6}?;", "");

        // 2.0 保存评论内容
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(currentTime);

        Comment comment = new Comment();
        comment.setVideoId(videoId);
        comment.setUserId(user.getId());
        comment.setLoginName(user.getLoginName());
        comment.setContent(content);
        comment.setUpdateTime(now);

        iCommentDAO.insert(comment);
    }

    /**
     * 获取某个视频的评论列表
     * @param videoId 视频主键
     * @return
     */
    public List<Comment> listByVideoId(String videoId) {
        List<Comment> list = iCommentDAO.listByVideoId(videoId);
        if (list!=null && list.isEmpty()==false) {
            int len = list.size();
            for (int i=0; i<len; i++) {
                // 查询该评论下的回复列表
                List<Reply> replyLsit = iReplyDAO.listByCommentId(list.get(i).getId());
                list.get(i).setReplyList(replyLsit);
            }
        }

        return list;
    }

    /**
     * 获取某个视频的评论条数
     * @param videoId 视频id
     * @return
     */
    public int getCommentCount(String videoId) {
        // 评论条数
        int commentCount = iCommentDAO.countByVideoId(videoId);
        // 回复条数
        int replyCount = iReplyDAO.countByVideoId(videoId);

        return (commentCount+replyCount);
    }
    
}
