//package com.example.rabbitmq.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author szy
// * @version 1.0
// * @description
// * @date 2021-11-05 18:10:31
// */
//
//@Configuration
//public class RabbitConfig {
//
//    private static final String DEAD_LETTERS_QUEUE_NAME = "dead_letters_queue_name";
//    /**
//     * 死信交换机
//     *  下边绑定的队列为私信队列
//     */
//    private static final String DEAD_LETTERS_EXCHANGE_NAME = "dead_letters_exchange_name";
//    private static final String QUEUE_NAME = "test_dlx_queue_name";
//    private static final String EXCHANGE_NAME = "test_dlx_exchange_name";
//    private static final String ROUTING_KEY = "order.add";
//
//    /**
//     * 声明死信队列、死信交换机、绑定队列到死信交换机
//     * 建议使用FanoutExchange广播式交换机
//     */
//    @Bean
//    public Queue deadLettersQueue() {
//        return new Queue(DEAD_LETTERS_QUEUE_NAME);
//    }
//
//    @Bean
//    public FanoutExchange deadLettersExchange() {
//        return new FanoutExchange(DEAD_LETTERS_EXCHANGE_NAME);
//    }
//
//    @Bean
//    public Binding deadLettersBinding() {
//        return BindingBuilder.bind(deadLettersQueue()).to(deadLettersExchange());
//    }
//
//    /**
//     * 声明普通队列，并指定相应的备份交换机、死信交换机
//     */
//    @Bean
//    public Queue queue() {
//        Map<String, Object> arguments = new HashMap<>(10);
//        //指定死信发送的Exchange
//        arguments.put("x-dead-letter-exchange", DEAD_LETTERS_EXCHANGE_NAME);
//        return new Queue(QUEUE_NAME, true, false, false, arguments);
//    }
//
//    @Bean
//    public Exchange exchange() {
//        return new FanoutExchange(EXCHANGE_NAME, true, false, null);
//    }
//
//    @Bean
//    public Binding binding() {
//        return BindingBuilder.bind(queue()).to(exchange()).with(ROUTING_KEY).noargs();
//    }
//
//
//
//
//    @Bean("rabbitListenerContainerFactory")
//    public SimpleRabbitListenerContainerFactory pointTaskContainerFactory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
//                                                                          ConnectionFactory connectionFactory) {
//        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
//        factory.setConcurrentConsumers(20);
//        factory.setMessageConverter(new Jackson2JsonMessageConverter());
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//        factory.setPrefetchCount(10);
//        configurer.configure(factory, connectionFactory);
//        return factory;
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate template = new RabbitTemplate(connectionFactory);
//        template.setMessageConverter(new Jackson2JsonMessageConverter());
//        return template;
//    }
//
//}
