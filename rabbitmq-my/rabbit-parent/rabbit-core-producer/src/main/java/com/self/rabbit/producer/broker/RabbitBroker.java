package com.self.rabbit.producer.broker;


import com.self.rabbit.api.Message;
import com.self.rabbit.api.SendCallback;

/**
 * $RabbitBroker 具体发送不同种类型消息的接口
 */
public interface RabbitBroker {

    void rapidSend(Message message);

    void confirmSend(Message message);

    void reliableSend(Message message);

    void sendMessages();

    void sendAndCallBack(Message message, SendCallback sendCallback);

}
