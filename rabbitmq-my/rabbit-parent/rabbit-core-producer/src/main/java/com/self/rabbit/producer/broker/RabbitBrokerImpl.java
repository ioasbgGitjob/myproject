package com.self.rabbit.producer.broker;

import com.self.rabbit.api.Message;
import com.self.rabbit.api.MessageType;
import com.self.rabbit.api.SendCallback;
import com.self.rabbit.producer.constant.BrokerMsgConst;
import com.self.rabbit.producer.constant.BrokerMsgStatusEnum;
import com.self.rabbit.producer.entity.BrokerMessage;
import com.self.rabbit.producer.service.MessageStoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;
import java.util.List;

/**
 * $RabbitBrokerImpl 真正的发送不同类型的消息实现类
 *
 * @author szy
 */

@Component
@Slf4j
public class RabbitBrokerImpl implements RabbitBroker {

    @Autowired
    RabbitTemplateContainer rabbitTemplateContainer;

    @Autowired
    MessageStoreService messageStoreService;


    /**
     * 发送消息的核心方法
     *
     * @param message
     */
    private void sendKernel(Message message) {
        // 异步线程池
        AsyncBaseQueue.submit(() -> {
            CorrelationData correlationData =
                    new CorrelationData(String.format("%s#%s#%s",
                            message.getMessageId(),
                            System.currentTimeMillis(),
                            message.getMessageType())
                    );
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            // 从线程池获取rabbitTempalte对象,  并发送消息
            RabbitTemplate template = rabbitTemplateContainer.getTemplate(message);
            template.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });
    }

    private void sendKernel(Message message, SendCallback sendCallback) {
        // 异步线程池
        AsyncBaseQueue.submit(() -> {
            CorrelationData correlationData =
                    new CorrelationData(String.format("%s#%s#%s",
                            message.getMessageId(),
                            System.currentTimeMillis(),
                            message.getMessageType())
                    );
            correlationData.getFuture().addCallback(new ListenableFutureCallback() {
                @Override
                public void onFailure(Throwable ex) {

                }

                @Override
                public void onSuccess(Object result) {

                }
            });
            String topic = message.getTopic();
            String routingKey = message.getRoutingKey();
            // 从线程池获取rabbitTempalte对象,  并发送消息
            RabbitTemplate template = rabbitTemplateContainer.getTemplate(message);
            template.convertAndSend(topic, routingKey, message, correlationData);
            log.info("#RabbitBrokerImpl.sendKernel# send to rabbitmq, messageId: {}", message.getMessageId());
        });
    }


    @Override
    public void rapidSend(Message message) {
        message.setMessageType(MessageType.RAPID);
        sendKernel(message);
    }

    @Override
    public void confirmSend(Message message) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message);
    }

    @Override
    public void reliableSend(Message message) {
        message.setMessageType(MessageType.RELIANT);
        BrokerMessage msg = messageStoreService.selectByMessageId(message.getMessageId());
        if (msg == null) {
            // 1 先把消息入库
            BrokerMessage brokerMessage = new BrokerMessage();
            brokerMessage.setMessageId(message.getMessageId());
            brokerMessage.setStatus(BrokerMsgStatusEnum.SEND_OK.getCode());
            //tryCount 在最开始发送的时候不需要进行设置
            brokerMessage.setNextRetry(LocalDateTime.now().plusMinutes(BrokerMsgConst.TIMEOUT));
            brokerMessage.setCreateTime(LocalDateTime.now());
            brokerMessage.setUpdateTime(LocalDateTime.now());
            brokerMessage.setMessage(message);
            messageStoreService.save(brokerMessage);
        }
        // 2 发送消息
        sendKernel(message);
    }

    @Override
    public void sendMessages() {
        List<Message> messages = MessageHolder.clear();
        messages.forEach(message -> {
            MessageHolderAyncQueue.submit(() -> {
                CorrelationData correlationData =
                        new CorrelationData(String.format("%s#%s#%s",
                                message.getMessageId(),
                                System.currentTimeMillis(),
                                message.getMessageType()));
                String topic = message.getTopic();
                String routingKey = message.getRoutingKey();
                RabbitTemplate rabbitTemplate = rabbitTemplateContainer.getTemplate(message);
                rabbitTemplate.convertAndSend(topic, routingKey, message, correlationData);
                log.info("#RabbitBrokerImpl.sendMessages# send to rabbitmq, messageId: {}", message.getMessageId());
            });
        });
    }

    @Override
    public void sendAndCallBack(Message message, SendCallback sendCallback) {
        message.setMessageType(MessageType.CONFIRM);
        sendKernel(message, sendCallback);
    }

}
