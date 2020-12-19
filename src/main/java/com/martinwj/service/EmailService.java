package com.martinwj.service;

import com.martinwj.entity.Email;
import com.martinwj.exception.SysException;

import java.util.List;

/**
 * @ClassName: EmailService
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
public interface EmailService {

    /**
     * 查询邮件设置列表
     */
    public List<Email> list();

    /**
     * 保存邮件设置
     *
     * @param emailList
     * @throws SysException
     */
    public void save(List<Email> emailList) throws SysException;

    /**
     * 删除邮件设置
     *
     * @param idArr 邮件设置主键数组
     * @throws SysException
     */
    public void delete(String[] idArr) throws SysException;

    /**
     * 检测邮件发送
     *
     * @param id      邮件设置表的主键
     * @param toEmail 收件人的邮箱
     * @throws SysException
     */
    public void emailTest(String id, String toEmail) throws SysException;

    /**
     * 发送邮件
     *
     * @param toEmail 收件人邮箱地址
     * @param subject 邮件标题
     * @param content 发送内容
     * @throws SysException
     */
    public void sendEmail(String toEmail, String subject, String content) throws SysException;

    /**
     * 获取邮件发送异常对应的错误信息
     *
     * @param error
     * @throws SysException
     */
    void getErrorMessage(String error) throws SysException;

}
