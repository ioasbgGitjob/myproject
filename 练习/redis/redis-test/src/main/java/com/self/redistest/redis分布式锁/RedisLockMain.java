package com.self.redistest.redis分布式锁;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author szy
 * @version 1.0
 * @description redis分布式锁调用
 */

@Component
@Slf4j
public class RedisLockMain {

    @Autowired
    private RedisTemplate redisTemplate;

    public String redisLockImooc(){
        log.info("我进入了方法！");
        try (RedisLockImooc redisLockImooc = new RedisLockImooc(redisTemplate,"redisKey",30)){
            if (redisLockImooc.getLock()) {
                log.info("我进入了锁！！");
                Thread.sleep(15000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("方法执行完成");
        return "方法执行完成";
    }
}
