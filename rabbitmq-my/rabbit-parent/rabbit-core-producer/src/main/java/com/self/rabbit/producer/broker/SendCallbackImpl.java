package com.self.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author szy
 * @version 1.0
 * @description 处理回调结果
 */
@Slf4j
public class SendCallbackImpl implements ListenableFutureCallback {

    @Override
    public void onFailure(Throwable ex) {
        log.error("消息消费成功,业务逻辑失败", ex);
    }

    @Override
    public void onSuccess(Object result) {

       log.info("消息消费成功, 业务逻辑执行成功,result:",result);
    }
}
