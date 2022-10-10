package com.self.rabbit.producer.entity;


import com.self.rabbit.api.Message;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * $BrokerMessage 消息记录表实体映射
 *
 * @author szy
 */
public class BrokerMessage implements Serializable {

    private static final long serialVersionUID = -8877774376788555834L;

    private String messageId;

    private Message message;

    private Integer tryCount = 0;

    private String status;

    private LocalDateTime nextRetry;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId == null ? null : messageId.trim();
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public LocalDateTime getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(LocalDateTime nextRetry) {
        this.nextRetry = nextRetry;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}