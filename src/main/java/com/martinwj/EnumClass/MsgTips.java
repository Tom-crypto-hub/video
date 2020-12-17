package com.martinwj.EnumClass;

/**
 * @ClassName: MsgTips
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-02
 */
public enum MsgTips {
    ERROR("404"),
    WRONG("500"),
    TIPS("提示信息"),
    MESSAGE("通知消息"),
    OTHER("其他自定义");

    // 响应消息主题内容
    private String tips;

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    MsgTips(String tips){
        this.tips = tips;
    }

}
