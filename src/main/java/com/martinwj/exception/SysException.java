package com.martinwj.exception;

/**
 * @ClassName: SysException
 * @Description: TODO  系统自定义异常类，抛出预期异常信息
 * @author: martin-wj
 * @createDate: 2020-12-17
 */
public class SysException extends Exception{

    private static final long serialVersionUID = 1L;

    // 异常信息
    public String message;

    public SysException(String message){
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
