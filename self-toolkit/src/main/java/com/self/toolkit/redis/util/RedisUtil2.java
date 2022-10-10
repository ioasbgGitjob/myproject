package com.self.toolkit.redis.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-01-14 15:42:20
 */

public class RedisUtil2 implements IGlobalCache{

    private StringRedisTemplate stringRedisTemplate;

    public RedisUtil2(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
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
        return stringRedisTemplate.keys("*");
    }
}
