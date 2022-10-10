package com.self.rabbit.producer.service;

import com.self.rabbit.producer.constant.BrokerMsgStatusEnum;
import com.self.rabbit.producer.entity.BrokerMessage;
import com.self.rabbit.producer.mapper.BrokerMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author szy
 * @description 消息入库操作
 */

@Service
public class MessageStoreService {

    @Autowired
    BrokerMessageMapper brokerMessageMapper;


    public void save(BrokerMessage brokerMessage) {
        brokerMessageMapper.insert(brokerMessage);
    }

    public void success(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(
                messageId,
                BrokerMsgStatusEnum.SEND_OK.getCode(),
                LocalDateTime.now()
        );
    }

    public void failure(String messageId) {
        brokerMessageMapper.changeBrokerMessageStatus(
                messageId,
                BrokerMsgStatusEnum.SEND_FAIL.getCode(),
                LocalDateTime.now()
        );
    }

    public BrokerMessage selectByMessageId(String messageId) {
        return this.brokerMessageMapper.selectByPrimaryKey(messageId);
    }


    public List<BrokerMessage> fetchTimeOutMessage4Retry(BrokerMsgStatusEnum brokerMessageStatus) {
        return this.brokerMessageMapper.queryBrokerMessageStatus4Timeout(brokerMessageStatus.getCode());
    }

    public int updateTryCount(String brokerMessageId) {
        return this.brokerMessageMapper.update4TryCount(brokerMessageId, LocalDateTime.now());
    }


}
