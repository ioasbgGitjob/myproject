package com.example.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;


/**
 * @CreateTime : 2019/9/3
 * @Description : 生产者
 **/
@RestController
public class Producer {
    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    private static final String EXCHANGE_NAME = "test_dlx_exchange_name";
    private static final String ROUTING_KEY = "order.add";

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage() throws Exception {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间(60秒过期)
            int expiration = 1000 * 60;
            messageProperties.setExpiration(String.valueOf(expiration));
            return message;
        };

        //模拟创建五条订单消息
        for (int i = 0; i < 5; i++) {
            String orderId = String.valueOf(i + "_60s");
            //订单初始化状态都为未支付
            logger.info("生产者接收到订单：" + orderId);
            Thread.sleep(100);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, orderId, messagePostProcessor, new CorrelationData(UUID.randomUUID().toString()));
        }
        return "60秒过期";
    }

    @GetMapping("/sendDirectMessage1")
    public String sendDirectMessage1() throws Exception {
        MessagePostProcessor messagePostProcessor = message -> {
            MessageProperties messageProperties = message.getMessageProperties();
            // 设置编码
            messageProperties.setContentEncoding("utf-8");
            // 设置过期时间(60秒过期)
            int expiration = 1000 * 10;
            messageProperties.setExpiration(String.valueOf(expiration));
            return message;
        };

        //模拟创建五条订单消息
        for (int i = 0; i < 5; i++) {
            String orderId = String.valueOf(i + "_10s");
            //订单初始化状态都为未支付
            logger.info("生产者接收到订单：" + orderId);
            Thread.sleep(100);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, orderId, messagePostProcessor, new CorrelationData(UUID.randomUUID().toString()));
        }
        return "10秒过期";
    }


}