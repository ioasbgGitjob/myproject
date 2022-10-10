package com.self.rabbit.api;

import com.self.rabbit.api.exception.MessageRunTimeException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * $MessageBuilder 建造者模式
 * 自动填充+参数校验
 *
 * @author szy
 */

public class MessageBuilder {

    private String messageId;
    private String topic;
    private String routingKey = "";
    private Map<String, Object> attributes = new HashMap<>();
    private int delayMills;
    private String messageType = MessageType.CONFIRM;

    private MessageBuilder() {
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder messageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    public MessageBuilder topic(String topic) {
        this.topic = topic;
        return this;
    }

    public MessageBuilder routingKey(String routingKey) {
        this.routingKey = routingKey;
        return this;
    }

    public MessageBuilder attributes(Map<String, Object> attributes) {
        this.attributes = attributes;
        return this;
    }

    public MessageBuilder attribute(String key, Object value) {
        attributes.put(key, value);
        return this;
    }

    public MessageBuilder delayMills(int delayMills) {
        this.delayMills = delayMills;
        return this;
    }

    public MessageBuilder messageType(String messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message build() {

        // check messageId
        if (messageId == null) {
            messageId = UUID.randomUUID().toString();
        }
        // topic is null
        if (topic == null) {
            throw new MessageRunTimeException("this topic is null");
        }
        return new Message(messageId, topic, routingKey, attributes, delayMills, messageType);
    }

}
