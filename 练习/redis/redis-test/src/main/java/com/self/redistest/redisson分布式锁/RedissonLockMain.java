package com.self.redistest.redisson分布式锁;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-06-28 16:02:34
 */

@Component
public class RedissonLockMain {

    @Autowired
    RedissonClient redissonClient;

    public void t1() {

        RLock lock = redissonClient.getLock("key");
        // 中间业务操作
        lock.unlock();

    }
}
