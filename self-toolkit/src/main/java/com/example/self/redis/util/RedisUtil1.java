package com.example.self.redis.util;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-01-14 15:42:20
 */

public class RedisUtil1 implements IGlobalCache{

    private RedisTemplate redisTemplate;

    public RedisUtil1(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public boolean set(String key, Object value) {
        return false;
    }

    @Override
    public Set<String> keys() {
        return redisTemplate.keys("*");
    }
}
