package com.martinwj.dao.reply;

import com.martinwj.entity.Reply;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName: IReplyDAO
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-26
 */
@Repository
public interface IReplyDAO {
    /**
     * 获取某个评论的回复列表
     * @param commentId 评论表主键
     * @return
     */
    List<Reply> listByCommentId(String commentId);

    /**
     * 获取某个视频的回复条数
     * @param videoId 视频主键
     * @return
     */
    int countByVideoId(String videoId);
}
