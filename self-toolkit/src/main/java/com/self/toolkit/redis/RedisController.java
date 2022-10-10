package com.self.toolkit.redis;

import com.self.toolkit.redis.util.IGlobalCache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-01-12 15:18:32
 */

@RestController
public class RedisController {


    @Resource(name = "stringRedisTemplate2")
    private StringRedisTemplate stringRedisTemplate2;

    @Resource(name = "redisTemplate")
    private RedisTemplate redisTemplate;


    @Resource(name = "redis1")
    IGlobalCache redis1;

    @Resource(name = "redis2")
    IGlobalCache redis2;

    @GetMapping("redis1")
    public String redis() {
        System.out.println(redisTemplate.opsForValue().getOperations().keys("*"));
        System.out.println(redisTemplate.keys("*"));
        return redis1.keys().toString() + "___" + redis2.keys();
    }

}
