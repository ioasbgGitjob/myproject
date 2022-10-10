package com.self.rabbit.producer.broker;

import com.google.common.base.Preconditions;
import com.self.rabbit.api.Message;
import com.self.rabbit.api.MessageProducer;
import com.self.rabbit.api.MessageType;
import com.self.rabbit.api.SendCallback;
import com.self.rabbit.api.exception.MessageRunTimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * $MessageClient 发送消息的实际实现类
 *
 * @author szy
 */

@Slf4j
@Component
public class MessageClient implements MessageProducer {


    @Autowired
    RabbitBroker rabbitBroker;

    @Override
    public void sendMessage(Message message, SendCallback callback) throws MessageRunTimeException {
        rabbitBroker.sendAndCallBack(message, callback);
    }

    @Override
    public void sendMessage(Message message) throws MessageRunTimeException {
        Preconditions.checkNotNull(message.getTopic());
        switch (message.getMessageType()) {
            case MessageType.RAPID -> rabbitBroker.rapidSend(message);
            case MessageType.CONFIRM -> rabbitBroker.confirmSend(message);
            case MessageType.RELIANT -> rabbitBroker.reliableSend(message);
            default -> log.error("消息类型不存在:" + message.getTopic());
        }
    }

    @Override
    public void sendMessages(List<Message> messages) throws MessageRunTimeException {
        messages.forEach(e -> {
            e.setMessageType(MessageType.RAPID);
            MessageHolder.add(e);
        });
        rabbitBroker.sendMessages();
    }
}
