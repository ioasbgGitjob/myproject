package com.self.rabbit.common.convert;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

/**
 * @author szy
 * @description rabbitMQ的消息转换
 * & 使用装饰着模式
 */

public class RabbitMessageConverter implements MessageConverter {

    private GenericMessageConverter genericMessageConverter;

    public RabbitMessageConverter(GenericMessageConverter genericMessageConverter) {
        this.genericMessageConverter = genericMessageConverter;
    }

    @Override
    public Message toMessage(Object obj, MessageProperties messageProperties) throws MessageConversionException {
        com.self.rabbit.api.Message message = (com.self.rabbit.api.Message) obj;
        // 设置延时队列
        // MessageProperties: Message Properties for an AMQP message.
        messageProperties.setDelay(message.getDelayMills());

        return genericMessageConverter.toMessage(obj, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return genericMessageConverter.fromMessage(message);
    }
}
