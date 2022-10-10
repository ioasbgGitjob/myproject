package com.self.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.rabbitmq.client.ReturnCallback;
import com.self.rabbit.api.Message;
import com.self.rabbit.api.MessageType;
import com.self.rabbit.common.convert.GenericMessageConverter;
import com.self.rabbit.common.convert.RabbitMessageConverter;
import com.self.rabbit.common.serializer.Serializer;
import com.self.rabbit.common.serializer.impl.JacksonSerializerFactory;
import com.self.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 池化封装 	每一个topic 对应一个RabbitTemplate
 * & 提高发送的效率
 * & 可以根据不同的需求制定化不同的RabbitTemplate, 比如每一个topic 都有自己的routingKey规则
 *
 * @author szy
 */

@Slf4j
@Component
public class RabbitTemplateContainer implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    @Autowired
    ConnectionFactory connectionFactory;
    @Autowired
    MessageStoreService messageStoreService;

    JacksonSerializerFactory jacksonSerializerFactory = JacksonSerializerFactory.INSTANCE;


    private Map<String/*topic*/, RabbitTemplate> rabbitTemplateMap = new ConcurrentHashMap<>();

    private Splitter splitter = Splitter.on("#");

    public RabbitTemplate getTemplate(Message message) {
        Preconditions.checkNotNull(message);
        String topic = message.getTopic();
        RabbitTemplate t = rabbitTemplateMap.get(topic);
        if (null != t) {
            return t;
        }
        // 新建一个rabbitTemplate
        log.info("#RabbitTemplateContainer.getTemplate# topic: {} is not exists, create one", topic);
        RabbitTemplate newTemplate = new RabbitTemplate(connectionFactory);
        newTemplate.setExchange(topic);
        newTemplate.setRoutingKey(message.getRoutingKey());
        newTemplate.setRetryTemplate(new RetryTemplate());

        // 添加序列化反序列化和converter对象
        Serializer serializer = jacksonSerializerFactory.create();
        GenericMessageConverter genericMessageConverter = new GenericMessageConverter(serializer);
        RabbitMessageConverter rabbitMessageConverter = new RabbitMessageConverter(genericMessageConverter);
        newTemplate.setMessageConverter(rabbitMessageConverter);

        //设置消息确认
        String msgType = message.getMessageType();
        if (!msgType.equals(MessageType.RAPID)) {
            newTemplate.setConfirmCallback(this::confirm);
            newTemplate.setReturnsCallback(this::returnedMessage);
        }
        rabbitTemplateMap.putIfAbsent(topic, newTemplate);
        return rabbitTemplateMap.get(topic);
    }


    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        List<String> strings = splitter.splitToList(correlationData.getId());
        String msgId = strings.get(0);
        String sendTime = strings.get(1);
        String msgType = strings.get(2);
        if (ack) {
            log.info("消息已经ack=true");
            //	当Broker 返回ACK成功时, 就是更新一下日志表里对应的消息发送状态为 SEND_OK
            // 	如果当前消息类型为reliant 我们就去数据库查找并进行更新
            if (MessageType.RELIANT.endsWith(msgType)) {
                messageStoreService.success(msgId);
            }
        } else {
            log.error("send message is Fail, confirm messageId: {}, sendTime: {}", msgId, sendTime);

        }
    }

    @Override
    public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msgJson = new String(message.getBody());
        System.out.println("return msg:" + msgJson);
    }

}
