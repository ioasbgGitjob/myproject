package com.self.toolkit.redis;

import com.self.toolkit.redis.util.IGlobalCache;
import com.self.toolkit.redis.util.RedisUtil1;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author szy
 * @version 1.0
 * @description 多个数据源redis,
 * 默认数据源自动装配为RedisTemplate, 并实例化为[redis1]
 * 第二个数据源redis2, 手动装配为StringRedisTemplate, 并实例化为[redis2]
 * @date 2022-01-11 18:21:36
 */
@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * @param factory yml配置文件按照这个写的话, spring会自动载入参数
     *                ### Redis数据库索引（默认为0）
     *                spring.redis.database=0
     *                spring.redis.host=192.168.209.11
     *                spring.redis.port=6379
     *                spring.redis.password=322@*sdaAs
     *                #Springboot2.0 不能设置为0
     *                spring.redis.timeout=600
     *                # 连接池最大连接数（使用负值表示没有限制）
     *                spring.redis.lettuce.pool.max-active=50
     *                # 连接池最大阻塞等待时间（使用负值表示没有限制）
     *                spring.redis.lettuce.pool.max-wait=-1
     *                # 连接池中的最大空闲连接
     *                spring.redis.lettuce.pool.max-idle=8
     *                # 连接池中的最小空闲连接
     *                pring.redis.lettuce.pool.min-idle=0
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate template = new RedisTemplate();
        template.setConnectionFactory(factory);
        // Json 序列化配置
        Jackson2JsonRedisSerializer<Object> objectJackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        // ObjectMapper 转译
        ObjectMapper objectMapper = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会报异
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        objectJackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        // String 的序列化
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key 采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash 的key也采用 String 的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value 序列化方式采用 jackson
        template.setValueSerializer(objectJackson2JsonRedisSerializer);
        // hash 的 value 采用 jackson
        template.setHashValueSerializer(objectJackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 手动注入参数
     *
     * @return
     */
    @Bean(name = "stringRedisTemplate2")
    public StringRedisTemplate redisUatTemplate(@Value("${spring.redis2.host}") String hostName,
                                                @Value("${spring.redis2.port}") int port,
                                                @Value("${spring.redis2.password}") String password,
                                                @Value("${spring.redis.lettuce.pool.max-idle}") int maxIdle,
                                                @Value("${spring.redis.lettuce.pool.max-active}") int maxTotal,
                                                @Value("${spring.redis2.database}") int index,
                                                @Value("${spring.redis.lettuce.pool.max-wait}") long maxWaitMillis,
                                                @Value("${spring.redis.lettuce.pool.min-idle}") int minIdle) {
        StringRedisTemplate temple = new StringRedisTemplate();
        temple.setConnectionFactory(
                connectionFactory(hostName, port, password, maxIdle, maxTotal, index, maxWaitMillis, minIdle));

        return temple;
    }

    public RedisConnectionFactory connectionFactory(String hostName, int port, String password, int maxIdle,
                                                    int maxTotal, int index, long maxWaitMillis, int minIdle) {
        JedisConnectionFactory jedis = new JedisConnectionFactory();
        jedis.setHostName(hostName);
        jedis.setPort(port);
        if (!StringUtils.isEmpty(password)) {
            jedis.setPassword(password);
        }
        if (index != 0) {
            jedis.setDatabase(index);
        }
        jedis.setPoolConfig(poolCofig(maxIdle, maxTotal, maxWaitMillis, minIdle));
        // 初始化连接pool
        jedis.afterPropertiesSet();
        RedisConnectionFactory factory = jedis;
        return factory;
    }

    public JedisPoolConfig poolCofig(int maxIdle, int maxTotal, long maxWaitMillis, int minIdle) {
        JedisPoolConfig poolCofig = new JedisPoolConfig();
        poolCofig.setMaxIdle(maxIdle);
        poolCofig.setMaxTotal(maxTotal);
        poolCofig.setMaxWaitMillis(maxWaitMillis);
        poolCofig.setMinIdle(minIdle);
        return poolCofig;
    }


    @Bean(name = "redis1")
    IGlobalCache cache(RedisTemplate redisTemplate) {
        return new RedisUtil1(redisTemplate);
    }

    @Bean(name = "redis2")
    IGlobalCache cache2(StringRedisTemplate stringRedisTemplate2) {
        return new RedisUtil1(stringRedisTemplate2);
    }


    // 配置文件

  /*
    ### Redis数据库索引（默认为0）
    spring.redis.database=0
    spring.redis.host=192.168.209.11
    spring.redis.port=6379
    spring.redis.password=322@*sdaAs
    #Springboot2.0 不能设置为0
    spring.redis.timeout=600
            # 连接池最大连接数（使用负值表示没有限制）
    spring.redis.lettuce.pool.max-active=50
            # 连接池最大阻塞等待时间（使用负值表示没有限制）
    spring.redis.lettuce.pool.max-wait=-1
            # 连接池中的最大空闲连接
    spring.redis.lettuce.pool.max-idle=8
            # 连接池中的最小空闲连接
    pring.redis.lettuce.pool.min-idle=0

            ## 第二数据源
    spring.redis2.database=0
    spring.redis2.host=192.168.209.11
    spring.redis2.port=6379
    spring.redis2.password=322@*sdaAs

    */
}
