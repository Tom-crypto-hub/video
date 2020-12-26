package com.martinwj.service;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.entity.Comment;
import com.martinwj.entity.User;
import com.martinwj.exception.SysException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @ClassName: CommentService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-26
 */
public interface CommentService {

    /**
     * 保存用户评论
     * @param videoId
     * @param content
     * @param user
     * @throws SysException
     */
    public void save(String videoId, String content, User user) throws SysException;

    /**
     * 获取某个视频的评论列表
     * @param videoId 视频主键
     * @return
     */
    public List<Comment> listByVideoId(String videoId);

    /**
     * 获取某个视频的评论条数
     * @param videoId 视频id
     * @return
     */
    public int getCommentCount(String videoId);

}
