package com.anan.document.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @Author anan
 * @Description TODO
 * @Date 2023/2/1 18:10
 * @Version 1.0
 */
public class EmailUtils {
    public static void sendEmail(String[] emails,String content) throws Exception {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "imap");  //smtp服务器
        props.put("mail.smtp.host", "smtp.exmail.qq.com");     //主机host
        props.put("mail.smtp.auth", "true");    //
        props.put("mail.smtp.port", "25");    //端口

        Session session = Session.getDefaultInstance(props, new Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("swt-noreply@henghuiic.com","Rjz321");
            }
        });

        session.setDebug(true);

        for (String email : emails) {
            Message message = createAttachMail(session,email,content);
            Transport.send(message);
        }

    }

    public static MimeMessage createAttachMail(Session session, String email,String content)
            throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("swt-noreply@henghuiic.com"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject("OA邮件提醒");
        message.setContent(content, "text/html;charset=UTF-8");
        return message;
    }
}
