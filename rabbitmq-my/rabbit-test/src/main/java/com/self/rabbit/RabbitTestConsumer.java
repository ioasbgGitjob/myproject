package com.self.rabbit;

import com.rabbitmq.client.Channel;
import com.self.rabbit.producer.broker.SendCallbackImpl;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author szy
 */

@Component
public class RabbitTestConsumer {



    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "exchange_2_queues", durable = "true"),
            exchange = @Exchange(name = "exchange_2",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.#"
    ))
    @RabbitHandler
    public void getRabbitmqMessage(Message message, Channel channel, SendCallbackImpl sendCallback) throws Exception {
        System.out.println("""
                我是迅速消息的消费者
                我消费啦一条消息
                """ + new String(message.getBody()));
        sendCallback.onSuccess("ok");
        System.out.println(1);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }


}
