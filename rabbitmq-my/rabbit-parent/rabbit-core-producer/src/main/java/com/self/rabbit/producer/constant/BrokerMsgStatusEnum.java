package com.self.rabbit.producer.constant;

/**
 * @author szy
 * @description 消息状态 枚举
 */

public enum BrokerMsgStatusEnum {

    SENDING("0"),
    SEND_OK("1"),
    SEND_FAIL("2"),
    SEND_FAIL_A_MOMENT("3");

    private String code;

    BrokerMsgStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
