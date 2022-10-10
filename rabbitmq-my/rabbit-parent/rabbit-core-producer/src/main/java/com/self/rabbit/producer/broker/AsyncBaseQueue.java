package com.self.rabbit.producer.broker;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 异步线程池 发送消息
 * @author szy
 */

@Slf4j
public class AsyncBaseQueue {

    private static final int THREAD_SIZE = Runtime.getRuntime().availableProcessors();

    private static final int QUEUE_SIZE = 10000;

    private static ThreadPoolExecutor senderAsync =
            new ThreadPoolExecutor(THREAD_SIZE,
                    THREAD_SIZE,
                    60L,
                    TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(QUEUE_SIZE),
                    r -> {
                        return new Thread(r, "rabbitmq_client_async_sender");
                    },
                    (r, executor) -> {
                        log.error("async sender is error rejected, runnable: {}, executor: {}", r, executor);
                    }
            );

    public static void submit(Runnable runnable) {
        senderAsync.submit(runnable);
    }
}
