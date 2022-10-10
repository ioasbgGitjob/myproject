package com.self.rabbit.api;

import com.self.rabbit.api.exception.MessageRunTimeException;

import java.util.List;

/**
 * @author szy
 */

public interface MessageProducer {

    /**
     *	$send消息的发送 附带SendCallback回调执行响应的业务逻辑处理
     * @param message
     * @param callback
     * @throws MessageRunTimeException
     */
    void sendMessage(Message message, SendCallback callback) throws MessageRunTimeException;

    /**
     * message消息的发送
     * @param message
     * @throws MessageRunTimeException
     */
    void sendMessage(Message message) throws MessageRunTimeException;

    /**
     * message消息批量发送
     * @param messages
     * @throws MessageRunTimeException
     */
    void sendMessages(List<Message> messages) throws MessageRunTimeException;

}
