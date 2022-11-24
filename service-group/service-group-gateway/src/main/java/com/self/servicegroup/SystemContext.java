package com.self.servicegroup;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统上下文，使用ThreadLocal记录数据。
 */
public class SystemContext {
    private static String SERVICE_GROUP_KEY = "service-group";

    private static transient ThreadLocal<Map<String, String>> contextMap = new ThreadLocal();


    public static Map<String, String> getContextMap() {
        if (contextMap.get() == null) {
            contextMap.set(new HashMap<>());
        }
        return (Map) contextMap.get();
    }

    public static void setContextMap(Map<String, String> contextMap) {
        SystemContext.contextMap.set(contextMap);
    }

    public static String get(String key) {
        Map<String, String> contextMap = getContextMap();
        return contextMap == null ? null : contextMap.get(key);
    }

    public static void put(String key, String value) {
        if (key == null) {
            throw new IllegalArgumentException("key must not be null.");
        }

        Map<String, String> contextMap = getContextMap();
        if (contextMap == null) {
            contextMap = new HashMap();
            setContextMap((Map) contextMap);
        }

        contextMap.put(key, value);
    }

    public static void setServiceGroup(String serviceGroup) {
        getContextMap().put(SERVICE_GROUP_KEY, serviceGroup);
    }

    public static String getServiceGroup() {
        Map<String, String> contextMap = getContextMap();
        return contextMap == null ? null : contextMap.get(SERVICE_GROUP_KEY);
    }

    public static void clean() {
        contextMap.remove();
    }
}
