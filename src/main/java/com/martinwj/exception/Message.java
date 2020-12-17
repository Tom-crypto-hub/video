package com.martinwj.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @ClassName: Message
 * @Description: TODO 提示消息信息
 * @author: martin-wj
 * @createDate: 2020-12-02
 */
@Data
@AllArgsConstructor
public class Message {
    // 消息主题
    private String title;
    // 消息内容
    private String context;
    // 返回的页面的url
    private String url;

}
