package com.self.utils.convert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;

/**
 * @author szy
 * @version 1.0
 * @description 各种数据类型互相转换
 * @date 2021-12-16 11:21:31
 */

public class CvtUtils {
    /**
     * @param clazz
     * @param map
     * @param isIgnoreError 是否忽略异常, true:遇到异常设置null
     * @return
     */
    public static <T> T map2Bean(Class<T> clazz, Map<String, Object> map, boolean isIgnoreError) {
        T t = null;
        try {
            t = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                field.setAccessible(true);
                if (map.containsKey(field.getName())) {
                    // 处理 java.sql.Timestamp  转 java.time.LocalDateTime问题
                    if (field.getType().equals(LocalDateTime.class)) {
                        field.set(t, getLocalDateTime(map, field, isIgnoreError));
                    } else {
                        field.set(t, map.get(field.getName()));
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            if (isIgnoreError) {
                return t;
            }
            throw new RuntimeException(e);
        }
        return t;
    }

    private static LocalDateTime getLocalDateTime(Map<String, Object> map, Field field, boolean isIgnoreError) {
        LocalDateTime localDateTime;
        try {
            Date date = (Date) map.get(field.getName());
            localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } catch (ClassCastException | NullPointerException e) {
            if (isIgnoreError) {
                return null;
            }
            throw new RuntimeException(e.getMessage(), e);
        }
        return localDateTime;
    }
}
