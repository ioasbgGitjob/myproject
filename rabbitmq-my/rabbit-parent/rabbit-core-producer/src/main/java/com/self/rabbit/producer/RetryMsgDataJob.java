//package com.self.rabbit.producer;
//
//import com.self.rabbit.producer.broker.RabbitBroker;
//import com.self.rabbit.producer.constant.BrokerMsgStatusEnum;
//import com.self.rabbit.producer.entity.BrokerMessage;
//import com.self.rabbit.producer.service.MessageStoreService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
///**
// * @author szy
// * @description 使用xxl-job 监控任务重试
// * ## 暂时用Scheduling代替
// */
//
//@Slf4j
//@Component
//public class RetryMsgDataJob {
//
//    private static final int MAX_RETRYCOUNT = 3;
//
//    @Autowired
//    MessageStoreService messageStoreService;
//
//    @Autowired
//    RabbitBroker rabbitBroker;
//
//    /**
//     * 每1分钟 执行一次  0 0/1 * * * ?
//     */
//    @Scheduled(cron = "0/20 * * * * ? ")
//    public void checkRabbitMessage() {
//        List<BrokerMessage> sends = messageStoreService.fetchTimeOutMessage4Retry(BrokerMsgStatusEnum.SENDING);
//        String msgId;
//        for (BrokerMessage send : sends) {
//            msgId = send.getMessageId();
//            if (send.getTryCount() >= MAX_RETRYCOUNT) {
//                messageStoreService.failure(msgId);
//                log.error("rabbitmq message retryed max time, the final message failed, messageId:", msgId);
//            } else {
//                //	每次重发的时候要更新一下try count字段
//                messageStoreService.updateTryCount(msgId);
//                // 	重发消息
//                rabbitBroker.reliableSend(send.getMessage());
//            }
//        }
//    }
//
//
//}
