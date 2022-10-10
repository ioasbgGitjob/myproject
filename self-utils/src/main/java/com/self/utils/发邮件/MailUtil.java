package com.self.utils.发邮件;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.sun.mail.pop3.POP3Folder;
import com.sun.mail.pop3.POP3Store;
import com.sun.mail.util.MailSSLSocketFactory;
import com.sun.security.sasl.Provider;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.search.*;
import java.io.*;
import java.security.Security;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @author szy
 * @version 1.0
 * @description 处理邮件工具类
 * 接收/发送服务器根据不同邮箱有不同的地址
 * 在邮箱设置里都可找到
 * @maven javax.mail 1.6.2
 * @date 2021-11-25 15:18:27
 */
@Slf4j
public class MailUtil {
    /**
     * 发送服务器
     * 腾讯企业邮箱(465)： smtp.exmail.qq.com
     * qq SMTP服务器（465或587）: smtp.qq.com
     */
    private static final String SEND_SMTP_HOST = "smtp.qq.com";
    private static final int SEND_SMTP_PORT = 465;

    /**
     * 接收服务器
     * 腾讯企业邮箱 :imap.exmail.qq.com(使用SSL，端口号993)
     * qq         : imap.qq.com (993)
     */
    private static final String RECEIVE_IMAP_HOST = "imap.qq.com";
    private static final int RECEIVE_IMAP_PORT = 993;

    /**
     * 接收服务器
     * qq POP3服务器（995）: pop.qq.com
     * 腾讯企业邮箱:        pop.exmail.qq.com ，使用SSL，端口号995
     */
    private static final String RECEIVE_POP_HOST = "pop.qq.com";
    private static final int RECEIVE_POP_PORT = 995;

    // 账号
    private String user;
    // 密码
    private String password;

    private IMAPFolder imapFolder = null;
    private IMAPStore imapStore = null;

    private POP3Store pop3Store = null;
    private POP3Folder pop3Folder = null;

    public MailUtil() {
//        this("your email address", "your email password");
        this("1633044042@qq.com", "pxndytcpyvcwdhcd");
    }

    public MailUtil(String user, String password) {
        this.user = user;
        this.password = password;
    }


    /**
     * 发送邮件
     *
     * @param toEmail 收件人邮箱地址
     * @param subject 邮件标题
     * @param content 邮件内容 可以是html内容
     */
    public void send(String toEmail, String subject, String content) {
        Session session = loadSendSession();
        // session.setDebug(true);
        // 创建邮件消息
        MimeMessage message = new MimeMessage(session);
        try {
            // 设置发件人
            message.setFrom(new InternetAddress(user));
            Address[] a = new Address[1];
            a[0] = new InternetAddress(user);
            message.setReplyTo(a);
            // 设置收件人
            InternetAddress to = new InternetAddress(toEmail);
            message.setRecipient(MimeMessage.RecipientType.TO, to);
            // 设置邮件标题
            message.setSubject(subject);
            // 设置邮件的内容体
            message.setContent(content, "text/html;charset=UTF-8");
            // 发送邮件
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }
    }


    /**
     * 发送邮件 带附件
     *
     * @param toEmail    收件人邮箱地址
     * @param subject    邮件标题
     * @param content    邮件内容 可以是html内容
     * @param attachPath 附件路径
     */
    public void send(String toEmail, String subject, String content, String attachPath) {
        Session session = loadSendSession();

        MimeMessage mm = new MimeMessage(session);
        try {
            //发件人
            mm.setFrom(new InternetAddress(user));
            //收件人
            mm.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail)); // 设置收件人
            // mm.setRecipient(Message.RecipientType.CC, new
            // InternetAddress("XXXX@qq.com")); //设置抄送人
            //标题
            mm.setSubject(subject);
            //内容
            Multipart multipart = new MimeMultipart();
            //body部分
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(content, "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            //附件部分
            BodyPart attachPart = new MimeBodyPart();
            FileDataSource fileDataSource = new FileDataSource(attachPath);
            attachPart.setDataHandler(new DataHandler(fileDataSource));
            attachPart.setFileName(MimeUtility.encodeText(fileDataSource.getName()));
            multipart.addBodyPart(attachPart);

            mm.setContent(multipart);
            Transport.send(mm);
        } catch (Exception e) {
            e.printStackTrace();
            String err = e.getMessage();
            // 在这里处理message内容， 格式是固定的
            System.out.println(err);
        }

    }

    public List<MailAttachment> getAllFileByDate(Long startDat, Long endDate) {
        return getAllFileByDate(startDat, endDate, null, null);
    }

    /**
     * 根据日期区间 获取所有邮件的所有附件
     *
     * @param startDat  开始时间 GE
     * @param endDate   结束时间 LE
     * @param fileNames 附件名
     * @param subject   邮件主题 ,有多个的话用任意符号分割
     * @return
     */
    public List<MailAttachment> getAllFileByDate(Long startDat, Long endDate, List<String> fileNames, String subject) {
        List<MailAttachment> result = new ArrayList<>();
        try {
            if (null == startDat)
                startDat = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (null == endDate || "".equals(endDate))
                endDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Message[] messages = getPopMessages(startDat, endDate);
            for (Message message : messages) {
                if (StrUtil.isNotBlank(subject) && !subject.contains(getSubject(message))) {
                    continue;
                }
                try {
                    List<MailAttachment> mailAttachments = new ArrayList<>();
                    getAttachment(message, mailAttachments, fileNames);
                    result.addAll(mailAttachments);
                } catch (Exception e) {
                    log.error("获取附件异常", e);
                    e.printStackTrace();
                }
            }
            return result;
        } catch (MessagingException e) {
            log.error("获取附件异常", e);
            e.printStackTrace();
        }
        return result;
    }

    public void getAllFileByTitle() {

    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private Session loadSendSession() {
        try {
            // 配置发送邮件的环境属性
            final Properties props = new Properties();
            // 表示SMTP发送邮件，需要进行身份验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", SEND_SMTP_HOST);
            // props.put("mail.smtp.port", SEND_SMTP_PORT);
            // 如果使用ssl，则去掉使用25端口的配置，进行如下配置,
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.port", SEND_SMTP_PORT);
            props.put("mail.smtp.port", SEND_SMTP_PORT);
            // 发件人的账号
            props.put("mail.user", user);
            // 访问SMTP服务时需要提供的密码
            props.put("mail.password", password);
            // 构建授权信息，用于进行SMTP进行身份验证
            Authenticator authenticator = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    // 用户名、密码
                    String userName = props.getProperty("mail.user");
                    String password = props.getProperty("mail.password");
                    return new PasswordAuthentication(userName, password);
                }
            };
            // 使用环境属性和授权信息，创建邮件会话
            return Session.getInstance(props, authenticator);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("mail session is null");
        }
        return null;
    }

    public void saveFile(List<MailAttachment> list) throws IOException {
        for (MailAttachment mailAttachment : list) {
            InputStream inputStream = mailAttachment.getInputStream();
            FileOutputStream outputStream = new FileOutputStream(new File("C:\\Users\\jiangyw\\Desktop\\test.pdf"));
            int len;
            byte[] bytes = new byte[1024];
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
            inputStream.close();
            outputStream.close();
        }

    }

    /**
     * 获取附件
     * 只获取附件里的
     * 邮件内容里的附件（图片等）忽略
     *
     * @param part 邮件中多个组合体中的其中一个组合体
     * @param list 附件容器
     * @throws UnsupportedEncodingException
     * @throws MessagingException
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void getAttachment(Part part, List<MailAttachment> list, List<String> fileNames) throws UnsupportedEncodingException, MessagingException,
            FileNotFoundException, IOException {
        if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();    //复杂体邮件
            //复杂体邮件包含多个邮件体
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                //获得复杂体邮件中其中一个邮件体
                BodyPart bodyPart = multipart.getBodyPart(i);
                //某一个邮件体也有可能是由多个邮件体组成的复杂体
                String disposition = bodyPart.getDisposition();
                if (disposition != null && (disposition.equalsIgnoreCase(Part.ATTACHMENT) || disposition.equalsIgnoreCase(Part.INLINE))) {

                    InputStream is = bodyPart.getInputStream();
                    // 附件名通过MimeUtility解码，否则是乱码
                    String name = MimeUtility.decodeText(bodyPart.getFileName());
                    if (CollectionUtil.isNotEmpty(fileNames)) {
                        if (fileNames.contains(name)) {
                            list.add(new MailAttachment(name, is));
                        }
                    } else {
                        list.add(new MailAttachment(name, is));
                    }
                } else if (bodyPart.isMimeType("multipart/*")) {
                    getAttachment(bodyPart, list, fileNames);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("name") || contentType.contains("application")) {

                    }
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getAttachment((Part) part.getContent(), list, fileNames);
        }
    }

    /**
     * 判断邮件中是否包含附件
     *
     * @return 邮件中是否存在附件
     * @throws MessagingException
     * @throws IOException
     */
    public boolean hasAttachment(Part part) throws MessagingException, IOException {
        boolean flag = false;
        if (part.isMimeType("multipart/*")) {
            MimeMultipart multipart = (MimeMultipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                String disp = bodyPart.getDisposition();
                if (disp != null && (disp.equalsIgnoreCase(Part.ATTACHMENT) || disp.equalsIgnoreCase(Part.INLINE))) {
                    flag = true;
                } else if (bodyPart.isMimeType("multipart/*")) {
                    flag = hasAttachment(bodyPart);
                } else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.contains("application")) {
                        flag = true;
                    }

                    if (contentType.contains("name")) {
                        flag = true;
                    }
                }
                if (flag) break;
            }
        } else if (part.isMimeType("message/rfc822")) {
            flag = hasAttachment((Part) part.getContent());
        }
        return flag;
    }

    /**
     * 获得邮件文本内容
     *
     * @param part        邮件体
     * @param textContent 存储邮件文本内容的字符串
     * @param htmlContent 存储邮件html内容的字符串
     * @throws MessagingException
     * @throws IOException
     */
    public void getMailTextContent(Part part, StringBuffer textContent, StringBuffer htmlContent) throws MessagingException, IOException {
        //如果是文本类型的附件，通过getContent方法可以取到文本内容，但这不是我们需要的结果，所以在这里要做判断
        boolean isContainTextAttach = part.getContentType().indexOf("name") > 0;
        if (part.isMimeType("text/*") && !isContainTextAttach) {
            String contentType = part.getContentType();
            if (contentType.startsWith("TEXT/PLAIN") || contentType.startsWith("text/plain")) {
                textContent.append(part.getContent().toString());
            }
            if (contentType.startsWith("TEXT/HTML") || contentType.startsWith("text/html")) {
                htmlContent.append(part.getContent().toString());
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailTextContent((Part) part.getContent(), textContent, htmlContent);
        } else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int partCount = multipart.getCount();
            for (int i = 0; i < partCount; i++) {
                BodyPart bodyPart = multipart.getBodyPart(i);
                getMailTextContent(bodyPart, textContent, htmlContent);
            }
        }
    }

    /**
     * 获取邮件主题
     *
     * @param message
     * @return
     * @throws MessagingException
     */
    public String getSubject(Message message) throws MessagingException {
        return message.getSubject();
    }

    /**
     * 获取邮件信息
     * IMAP协议
     * 下载附件的时候巨巨巨巨慢
     * 如果需要获取附件的时候不推荐使用
     * 该协议下只能查询receivedDate
     * sentDate都为null
     *
     * @throws MessagingException
     */
    public Message[] getImapMessages(Long startDate, Long endDate) throws MessagingException {
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        Security.addProvider(new Provider());
        System.setProperty("mail.mime.splitlongparameters", "false");
        Properties props = System.getProperties();
        props.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
        props.setProperty("mail.imap.socketFactory.port", "993");
        props.setProperty("mail.imapStore.protocol", "imap");
        props.setProperty("mail.imap.host", RECEIVE_IMAP_HOST);
        props.setProperty("mail.imap.port", "993");
        props.setProperty("mail.imap.auth.login.disable", "true");
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);

        imapStore = (IMAPStore) session.getStore("imap");  // 使用imap会话机制，连接服务器
        imapStore.connect(RECEIVE_IMAP_HOST, RECEIVE_IMAP_PORT, user, password);
        imapFolder = (IMAPFolder) imapStore.getFolder("INBOX"); //收件箱

        imapFolder.open(Folder.READ_ONLY);

        // 获取前一天的邮件信息 只能使用条件  ReceivedDateTerm
        SearchTerm startTerm = new ReceivedDateTerm(ComparisonTerm.GE, new Date(startDate));
        return imapFolder.search(startTerm);
    }

    private MailSSLSocketFactory getMailSslFactory() {
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            return sf;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过pop3协议获取邮件信息
     * pop3协议下只能通过sentDate来查询
     * receivedDate都为null
     *
     * @return Message[]
     * @throws MessagingException
     */
    public Message[] getPopMessages(Long startDate, Long endDate) throws MessagingException {
        Properties props = new Properties();
        props.setProperty("mail.imapStore.protocol", "pop3");       // 使用pop3协议
        props.setProperty("mail.pop3.port", "995");           // 端口
        props.setProperty("mail.pop3.host", RECEIVE_POP_HOST);       // pop3服务器
        props.put("mail.pop3.ssl.enable", true);
        props.put("mail.pop3.ssl.socketFactory", getMailSslFactory());
        Session session = Session.getInstance(props);
        pop3Store = (POP3Store) session.getStore("pop3");
        pop3Store.connect(RECEIVE_POP_HOST, RECEIVE_POP_PORT, user, password);

        // 获得收件箱
        pop3Folder = (POP3Folder) pop3Store.getFolder("INBOX");
        /* Folder.READ_ONLY：只读权限
         * Folder.READ_WRITE：可读可写（可以修改邮件的状态）
         */
        pop3Folder.open(Folder.READ_ONLY); //打开收件箱

        // 获取前一天的邮件信息
        SearchTerm endTerm = new SentDateTerm(ComparisonTerm.LE, new Date(endDate));
        SearchTerm startTerm = new SentDateTerm(ComparisonTerm.GE, new Date(startDate));
        SearchTerm searchTerm = new AndTerm(startTerm, endTerm);
//         得到收件箱中的所有邮件,并解析
        return pop3Folder.search(searchTerm);
    }

    /**
     * 关闭folder和store资源
     *
     * @throws MessagingException
     */
    public void close() throws MessagingException {
        if (imapFolder != null) {
            imapFolder.close(false);
        }
        if (imapStore != null) {
            imapStore.close();
        }
        if (pop3Folder != null) {
            pop3Folder.close(false);
        }
        if (pop3Store != null) {
            pop3Store.close();
        }
    }


}
