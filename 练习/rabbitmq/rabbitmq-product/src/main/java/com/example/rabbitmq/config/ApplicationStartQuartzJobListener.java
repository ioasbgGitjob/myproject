package com.example.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-09 18:43:44
 */

@Component
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * 启动
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            System.out.println("启动后立即加载...");
            for (int i = 0; i < 3; i++) {
                Thread.sleep(10000);
//                SendEmailUtils2.send("shizhuoyi@qiangyun.com", "线上所占内存邮件", "11");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}