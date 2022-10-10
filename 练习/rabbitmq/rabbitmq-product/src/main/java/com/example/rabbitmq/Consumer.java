package com.example.rabbitmq;


import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author szy
 * @version 1.0
 * @description 消费者
 * @date 2021-11-08 11:37:33
 */

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = "dead_letters_queue_name")
    public void handler1(Message message, Channel channel) throws IOException {

        byte[] body = message.getBody();
        String orderId = new String(body, StandardCharsets.UTF_8);
        logger.info("消费者接收到订单：" + orderId);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = "test_dlx_queue_name")
//    public void handler12(Message message, Channel channel) throws IOException {
//        // 从队列中取出订单号
//        byte[] body = message.getBody();
//        String orderId = new String(body, StandardCharsets.UTF_8);
//        logger.info("消费者接收到订单：" + orderId);
////        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//    }

}
