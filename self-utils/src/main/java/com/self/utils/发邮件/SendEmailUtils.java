package com.self.utils.发邮件;

import com.sun.mail.util.MailSSLSocketFactory;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.security.GeneralSecurityException;
import java.util.Properties;


/**
 * 发送邮件(简单示例)
 * @详细示例: {@link MailUtil}
 */
public class SendEmailUtils {

    public static void main(String[] args) throws Exception{
        SendEmailUtils.send("shizhuoyi@qiangyun.com", "线上所占内存邮件", "11");
    }

    public static String send(String sendEr,String header,String content) throws GeneralSecurityException {

        // 发件人电子邮箱
        String from = "1633044042@qq.com";
        String fromPassword = "pxndytcpyvcwdhcd";
        String filePath = "D:\\11.xlsx";
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器


        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, fromPassword);
            }
        });
        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendEr));
            // Set Subject: 头部头字段
            message.setSubject(header);
            // 设置消息体
            message.setText(content);
            // 附件
            //一个Multipart对象包含一个或多个bodypart对象，组成邮件正文
            MimeMultipart multipart = new MimeMultipart();
            //创建附件节点  读取本地文件,并读取附件名称
//            MimeBodyPart file1 = new MimeBodyPart();
//            DataHandler dataHandler2 = new DataHandler(new FileDataSource(filePath));
//            file1.setDataHandler(dataHandler2);
//            file1.setFileName(MimeUtility.encodeText(dataHandler2.getName()));
//            multipart.addBodyPart(file1);
//            message.setContent(multipart);
            // 发送消息
            Transport.send(message);
            return "success";
        }catch (Exception mex) {
            mex.printStackTrace();
        }
        return "fail";
    }
}
