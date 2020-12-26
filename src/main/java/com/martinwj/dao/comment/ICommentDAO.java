package com.martinwj.dao.comment;

import com.martinwj.entity.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: ICommentDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-26
 */
@Repository
public interface ICommentDAO {

    /**
     * 插入新的评论
     * @param comment
     * @return
     */
    int insert(Comment comment);

    /**
     * 获取某个视频的评论列表
     * @param videoId 视频主键
     * @return
     */
    List<Comment> listByVideoId(String videoId);

    /**
     * 获取某个视频的评论条数
     * @param videoId 视频主键
     * @return
     */
    int countByVideoId(String videoId);
}
