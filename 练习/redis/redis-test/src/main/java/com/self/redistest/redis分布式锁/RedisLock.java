package com.self.redistest.redis分布式锁;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author szy
 * @version 1.0
 * @description 实现分布式锁, 类似redisson的思路, 但是用threadLocal代替LUA脚本实现删除时候的原子性
 */

public interface RedisLock {

    boolean tryLock(String key, long timeOut, TimeUnit timeUnit);

    void releaseLock(String key);

}


/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-06-28 15:50:50
 */
@Component
class RedisLockImpl implements RedisLock {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    private ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Override
    public boolean tryLock(String key, long timeOut, TimeUnit timeUnit) {
        boolean lock = false;
        if (threadLocal.get() == null) {
            Thread thread = new Thread(() -> {
                while (true) {
                    stringRedisTemplate.expire(key, timeOut, timeUnit);
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {

                    }

                }
            });
            String uuid = thread.getId() + "_" + UUID.randomUUID();
            threadLocal.set(uuid);
            lock = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, timeOut, timeUnit);
            // 循环枷锁, 避免一次枷锁失败
            while (!lock) {
                lock = stringRedisTemplate.opsForValue().setIfAbsent(key, uuid, timeOut, timeUnit);
            }
        } else if (threadLocal.get().equals(stringRedisTemplate.opsForValue().get(key))) {
            // 在锁的状态
            return true;
        }
        return lock;
    }

    @Override
    public void releaseLock(String key) {
        String uuid = threadLocal.get();
        if (uuid.equals(stringRedisTemplate.opsForValue().get(key))) {
            // 关闭上边的线程
            Thread thread = findThread(Long.valueOf(uuid.split("_")[0]));
            thread.interrupt();
            // 删除 rediskey
            stringRedisTemplate.delete(key);
            threadLocal.remove();
        }
    }

    public static Thread findThread(long threadId) {
        ThreadGroup group = Thread.currentThread().getThreadGroup();
        while (group != null) {
            Thread[] threads = new Thread[(int) (group.activeCount() * 1.2)];
            for (int i = 0; i < threads.length; i++) {
                if (threadId == threads[i].getId()) {
                    return threads[i];
                }
            }
            group = group.getParent();
        }
        return null;
    }

}