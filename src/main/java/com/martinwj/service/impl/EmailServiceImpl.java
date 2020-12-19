package com.martinwj.service.impl;

import com.martinwj.constant.ErrorMsg;
import com.martinwj.dao.email.IEmailDAO;
import com.martinwj.dao.web.IWebDAO;
import com.martinwj.entity.Email;
import com.martinwj.entity.Web;
import com.martinwj.exception.SysException;
import com.martinwj.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

/**
 * @ClassName: EmailServiceImpl
 * @Description: TODO
 * @author: martin-wj
 * @createDate: 2020-12-18
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private IEmailDAO iEmailDAO;
    @Autowired
    private IWebDAO iWebDAO;

    /**
     * 查询邮件设置列表
     */
    public List<Email> list() {
        return iEmailDAO.list();
    }

    /**
     * 保存邮件设置
     *
     * @param emailList
     * @throws SysException
     */
    public void save(List<Email> emailList) throws SysException {
        for (Email email : emailList) {
            if (StringUtils.isEmpty(email.getId())) {
                // 插入
                iEmailDAO.insert(email);
            } else {
                // 更新
                iEmailDAO.update(email);
            }
        }
    }

    /**
     * 删除邮件设置
     *
     * @param idArr 邮件设置主键数组
     * @throws SysException
     */
    public void delete(String[] idArr) throws SysException {
        // 批量删除邮件设置
        iEmailDAO.delete(idArr);
    }

    /**
     * 检测邮件发送
     *
     * @param id      邮件设置表的主键
     * @param toEmail 收件人的邮箱
     * @throws SysException
     */
    public void emailTest(String id, String toEmail) throws SysException {
        Web web = iWebDAO.selectWebInfo();
        String webName = web.getName();

        // 返回错误信息
        String error = "";

        Email email = iEmailDAO.selectById(id);
        // 发信人邮件地址、密码
        final String fromaddress = email.getEmail();
        final String password = email.getPassword();

        // 创建Properties 类用于记录邮箱的一些属性
        Properties props = new Properties();
        // 表示SMTP发送邮件，必须进行身份验证
        props.put("mail.smtp.auth", "true");
        // 此处填写SMTP服务器
        props.put("mail.smtp.host", email.getSmtp());
        // 端口号
        props.put("mail.smtp.port", email.getPort());

        // 构建授权信息，用于进行SMTP进行身份验证
        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                // 发件人用户名、密码
                return new PasswordAuthentication(fromaddress, password);
            }
        };

        // 使用环境属性和授权信息，创建邮件会话
        Session session = Session.getInstance(props, authenticator);

        // 创建邮件消息
        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发件人
            if (StringUtils.isEmpty(webName)) {
                // 站点名称未设置时，使用默认昵称
                InternetAddress from = new InternetAddress(fromaddress);
                message.setFrom(from);
            } else {
                // 否则使用站点名称作为昵称
                String from = fromaddress;
                String nickname = "";
                try {
                    nickname = MimeUtility.encodeText(webName);
                } catch (UnsupportedEncodingException e) {
                    error = e.getMessage();
                    e.printStackTrace();
                }
                message.setFrom(new InternetAddress(nickname + " <" + from + ">"));
            }

            // 设置收件人的邮箱
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(RecipientType.TO, to);

            // 设置邮件标题
            message.setSubject("测试邮件");

            // 设置邮件的内容体
            String content = "";
            if (StringUtils.isEmpty(webName)) {
                content = "这是一封测试邮件";
            } else {
                content = "这是一封来自" + webName + "的测试邮件";
            }
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport.send(message);
        } catch (AddressException e) {
            error = e.getMessage();
        } catch (MessagingException e) {
            error = e.getMessage();
        } finally {
            getErrorMessage(error);
        }
    }

    /**
     * 发送邮件
     *
     * @param toEmail 收件人邮箱地址
     * @param subject 邮件标题
     * @param content 发送内容
     * @throws SysException
     */
    public void sendEmail(String toEmail, String subject, String content) throws SysException {
        // 获取站点信息
        Web web = iWebDAO.selectWebInfo();
        String webName = web.getName();

        // 返回错误信息
        String error = "";

        List<Email> list = iEmailDAO.list();
        if (list == null || list.size() == 0) {
            throw new SysException(ErrorMsg.ERROR_120008);
        }

        // 遍历邮件设置列表，尝试每一条记录，直到成功，或者全部失败
        for (Email email : list) {
            // 发信人邮件地址、密码
            final String fromaddress = email.getEmail();
            final String password = email.getPassword();

            // 创建Properties 类用于记录邮箱的一些属性
            Properties props = new Properties();
            // 表示SMTP发送邮件，必须进行身份验证
            props.put("mail.smtp.auth", "true");
            //此处填写SMTP服务器
            props.put("mail.smtp.host", email.getSmtp());
            //端口号
            props.put("mail.smtp.port", email.getPort());

            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 发件人用户名、密码
                    return new PasswordAuthentication(fromaddress, password);
                }
            };

            // 使用环境属性和授权信息，创建邮件会话
            Session session = Session.getInstance(props, authenticator);

            // 创建邮件消息
            MimeMessage message = new MimeMessage(session);
            try {
                // 设置发件人
                if (StringUtils.isEmpty(webName)) {
                    // 站点名称未设置时，使用默认昵称
                    InternetAddress from = new InternetAddress(fromaddress);
                    message.setFrom(from);
                } else {
                    // 否则使用站点名称作为昵称
                    String from = fromaddress;
                    String nickname = "";
                    try {
                        nickname = javax.mail.internet.MimeUtility.encodeText(webName);
                    } catch (UnsupportedEncodingException e) {
                        error = e.getMessage();
                        e.printStackTrace();
                    }
                    message.setFrom(new InternetAddress(nickname + " <" + from + ">"));
                }

                // 设置收件人的邮箱
                InternetAddress to = new InternetAddress(toEmail);
                message.setRecipient(MimeMessage.RecipientType.TO, to);

                // 设置邮件标题
                message.setSubject(subject);

                // 设置邮件的内容体
                message.setContent(content, "text/html;charset=UTF-8");

                // 发送邮件
                Transport.send(message);

                // 成功的话，直接返回
                break;
            } catch (AddressException e) {
                error = e.getMessage();
            } catch (MessagingException e) {
                error = e.getMessage();
            } finally {
                // 如果只有一条邮件设置的记录，则直接返回错误信息
                if (list.size() == 1) {
                    getErrorMessage(error);
                }
            }
        }

        // 全部遍历完，如果没有发送成功，则抛出异常
        if (!StringUtils.isEmpty(error)) {
            throw new SysException(ErrorMsg.ERROR_120007);
        }
    }

    /**
     * 获取邮件发送异常对应的错误信息
     *
     * @param error
     * @throws SysException
     */
    public void getErrorMessage(String error) throws SysException {
        if (!StringUtils.isEmpty(error)) {
            if (error.indexOf("422") >= 0) {
                throw new SysException(ErrorMsg.ERROR_120002);
            }
            if (error.indexOf("501") >= 0) {
                throw new SysException(ErrorMsg.ERROR_120003);
            }
            if (error.indexOf("503") >= 0) {
                throw new SysException(ErrorMsg.ERROR_120004);
            }
            if (error.indexOf("523") >= 0) {
                throw new SysException(ErrorMsg.ERROR_120004);
            }
            if (error.indexOf("535") >= 0) {
                throw new SysException(ErrorMsg.ERROR_120006);
            }

            throw new SysException(ErrorMsg.ERROR_120007);
        }
    }

}
