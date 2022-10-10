package com.self.rabbit;

import com.self.rabbit.api.Message;
import com.self.rabbit.api.MessageType;
import com.self.rabbit.producer.broker.MessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author szy
 * @description 初始化 发送消息
 */

@Component
public class InitApplicationEvent implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    MessageClient messageClient;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        for(int i = 0 ; i < 2; i ++) {
            String uniqueId = UUID.randomUUID().toString();
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("name", "张三");
            attributes.put("age", "18");
            Message message = new Message(
                    uniqueId,
                    "exchange_2",
                    "springboot.abc",
                    attributes,
                    0);
            message.setMessageType(MessageType.RELIANT);
//			message.setDelayMills(15000);
            messageClient.sendMessage(message);
        }
    }
}
