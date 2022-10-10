package com.self.utils.发邮件;

import cn.hutool.core.io.FileUtil;
import org.junit.Test;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author szy
 * @version 1.0
 * @description 获取邮件里的内容 并解析
 * @date 2021-11-25 15:30:16
 */

public class MailUtilTest {

    @Test
    public void sendMail() {
        MailUtil mailUtil = new MailUtil();
        mailUtil.send("shizhuoyi@qiangyun.com", "哇咔咔", "aaaaaa啊啊啊");

    }

    public static void main(String[] args) {
        System.out.println(LocalDate.parse("2021-01-01").atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }

    /**
     * 获取邮件的附件
     * pop3 协议
     * @throws MessagingException
     */
    @Test
    public void readMailPop3() throws Exception {
        Long startDat = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startTime = System.currentTimeMillis();
        MailUtil mailUtil = new MailUtil();
        Message[] aas = mailUtil.getPopMessages(startDat, endDate);
        for (Message aa : aas) {
            List<MailAttachment> mailAttachments = new ArrayList<>();
            mailUtil.getAttachment(aa, mailAttachments, null);
            for (MailAttachment item : mailAttachments) {
                FileUtil.writeFromStream(item.getInputStream(), new File("D:\\home\\data\\" + item.getName()));
            }
        }
        mailUtil.close();
        System.out.println("解析邮件共耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

    /**
     * 获取邮件的附件
     *
     * @throws MessagingException
     */
    @Test
    public void readMailImap() throws Exception {
        Long startDat = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        Long endDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long startTime = System.currentTimeMillis();
        MailUtil mailUtil = new MailUtil("1633044042@qq.com","dyeixbrtinkscfee");
        Message[] messages = mailUtil.getImapMessages(startDat, endDate);
        for (Message message : messages) {
            List<MailAttachment> mailAttachments = new ArrayList<>();
            mailUtil.getAttachment(message, mailAttachments, null);
            for (MailAttachment item : mailAttachments) {
                FileUtil.writeFromStream(item.getInputStream(), new File("D:\\home\\data\\" + item.getName()));
            }
        }
        mailUtil.close();
        System.out.println("解析邮件共耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
    }

}
