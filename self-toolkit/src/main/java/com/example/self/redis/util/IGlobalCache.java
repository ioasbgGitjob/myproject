package com.example.self.redis.util;

import java.util.Set;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-01-14 15:39:50
 */

public interface  IGlobalCache {

    Object get(String key);


    boolean set(String key, Object value);

    Set<String > keys();
}
