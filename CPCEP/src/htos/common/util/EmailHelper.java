/**
 * 
 */
package htos.common.util;

import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author 温勋
 * @ClassName : EmailHelper
 * @Version V1.1
 * @ModifiedBy 温勋
 * @Copyright 华胜龙腾
 * @date 2018年6月25日 下午4:26:31
 */
public class EmailHelper {
	private static final ResourceBundle bundle = ResourceBundle.getBundle("mail");
	private static final String sendFrom = bundle.getString("email.from");
	private static final String username = bundle.getString("email.username");
	private static final String password = bundle.getString("email.password");
	private static final String smtp_host = bundle.getString("email.host");

	public static void sendMail(String subject, String content, String to) {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", smtp_host);
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress(sendFrom));
            message.setRecipient(RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=utf-8");
            Transport transport = session.getTransport();
            transport.connect(smtp_host, username, password);
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("邮件发送失败...");
        }
    }
//	
//	public static void main(String[] args) {
//        sendMail("测试邮件", "测试邮件集成，滴滴答答", "820943868@qq.com");
//    }
}
