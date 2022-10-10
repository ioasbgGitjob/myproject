package com.self.toolkit.redis.缓存击穿;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author szy
 * @version 1.0
 * @description 处理缓存击穿
 * 处理方案: 设置key的逻辑过期时间
 * @date 2022-06-06 14:16:22
 */
public class RedisTest1 {
    private RedisTemplate redisTemplate;

    public RedisTest1(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getValue(String key) {
        Object value = redisTemplate.opsForValue().get(key);
        if (null == value) {
            Object lock = redisTemplate.opsForValue().get(key + "_lock");
            if (lock != null) {
                return "redis default value";
            } else {
                // 1. 设置锁
                redisTemplate.opsForValue().set(key + "_lock", System.currentTimeMillis());
                // 2. 执行数据库操作
                return "数据库查出来的值";
            }
        } else {
            return value;
        }
    }


}
