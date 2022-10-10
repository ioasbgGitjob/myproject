package com.self.toolkit.aspect.dataProtection;


import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

/**
 * 数据保护，加解密工具注解解析
 * 示例：
 * <pre>{@code
 * @Configuration
 * public class DataProtectionConfig {
 *     @Bean
 *     public DataProtectionAspect dataProtectionAspect(){
 *         return new DataProtectionAspect();
 *     }
 * }
 * }</pre>
 * {@link DataProtection}
 */
@Component
@Aspect
public class DataProtectionAspect implements InitializingBean {
    private static Crypt encryptUtil ;
    private static Crypt decryptUtil ;
    private static Map<Method, String[]> methodParameterNamesCache = new ConcurrentHashMap<>();
    private static List<Class> clazzs = Arrays.asList(
            byte.class,Byte.class
            ,short.class,Short.class
            ,int.class,Integer.class
            ,long.class,Long.class
            ,float.class,Float.class
            ,double.class,Double.class
            ,char.class,Character.class
            ,boolean.class,Boolean.class
    );

    private Consumer consumer;

    public DataProtectionAspect(Consumer consumer) {
        this.consumer = consumer;
    }

    public DataProtectionAspect() {
    }

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    public void afterPropertiesSet() {
        // 设置加密字符串
        String dataProtectionAesKey = applicationContext.getEnvironment()
                .getProperty("data.protection.aes.key");
        if (StrUtil.isBlank(dataProtectionAesKey)) {
            dataProtectionAesKey = "这里是加密的密钥";
        }
        encryptUtil = new EncryptUtil(dataProtectionAesKey);
        decryptUtil = new DecryptUtil(dataProtectionAesKey);
    }

    @Pointcut("@annotation(com.self.toolkit.aspect.dataProtection.DataProtection)")
    public void dataProtection() {
    }

    @Around("dataProtection()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        //获取方法签名
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取切入方法的对象
        Method method = signature.getMethod();
        //获取运行时参数的名称
        String[] parameterNames = getParameterNames(method);
        //获取方法上的Aop注解
        DataProtection dataProtection = method.getAnnotation(DataProtection.class);
        // 只对参数进行加解密
        execParameter(dataProtection, args, parameterNames);
        Object proceed = joinPoint.proceed(args);
        // 只对结果进行加解密
        proceed = execResult(dataProtection, proceed);
        if (consumer != null) {
            consumer.accept(proceed);
        }
        return proceed;
    }

    public static String[] getParameterNames(Method method) {
        String[] parameterNames = methodParameterNamesCache.get(method);
        if (parameterNames == null) {
            parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method);
            if (parameterNames != null) {
                methodParameterNamesCache.put(method,parameterNames);
            }
        }
        return parameterNames;
    }

    public static void execParameter(DataProtection dataProtection, Object[] args, String[] parameterNames) {
        String[] encrypts = dataProtection.encrypt();
        for (String encrypt : encrypts) {
            execParameter(args, parameterNames, encrypt, encryptUtil);
        }
        String[] decrypts = dataProtection.decrypt();
        for (String decrypt : decrypts) {
            execParameter(args, parameterNames, decrypt, decryptUtil);
        }
    }

    private static void execParameter(Object[] args, String[] parameterNames, String allEl, Crypt crypt) {
        allEl = allEl.replace("#","");
        if (allEl.endsWith(".")) {
            throw new RuntimeException("不能以.结尾");
        }
        if (isResult(allEl)) {
            return;
        }
        int index = allEl.indexOf(".");
        String el;
        String key = "";
        if (index == -1) {
            el = allEl;
        } else {
            el = allEl.substring(0,index);
            key = allEl.substring(index+1);
        }
        for (int i = 0; i < parameterNames.length; i++) {
            if (el.equals(parameterNames[i])) {
                args[i] = exec(args[i],key,crypt);
                break;
            }
        }
    }

    public static Object execResult(DataProtection dataProtection, Object proceed) {
        if (proceed == null) {
            return proceed;
        }
        String[] encrypts = dataProtection.encrypt();
        for (String encrypt : encrypts) {
            encrypt = encrypt.replace("#","");
            if (encrypt.endsWith(".")) {
                throw new RuntimeException("不能以.结尾");
            }
            if (isResult(encrypt)) {
                int index = encrypt.indexOf(".");
                if (index == -1) { // 只有result
                    proceed = exec(proceed,"",encryptUtil);
                } else {
                    proceed = exec(proceed, encrypt.substring(index + 1), encryptUtil);
                }
            }
        }
        String[] decrypts = dataProtection.decrypt();
        for (String decrypt : decrypts) {
            decrypt = decrypt.replace("#","");
            if (decrypt.endsWith(".")) {
                throw new RuntimeException("不能以.结尾");
            }
            if (isResult(decrypt)) {
                int index = decrypt.indexOf(".");
                if (index == -1) { // 只有result
                    proceed = exec(proceed, "", decryptUtil);
                } else {
                    proceed = exec(proceed, decrypt.substring(index+1), decryptUtil);
                }
            }
        }
        return proceed;
    }

    private static Object exec(Object source, String el, Crypt crypt) {
        if (source == null) {
            return source;
        }
        if (isNative(source)) {
            throw new RuntimeException("不能对非String类型进行加解密");
        }
        int index = el.indexOf(".");
        String key = "";
        String endKey = "";
        if (index == -1) {
            key = el;
        } else {
            key = el.substring(0,index);
            endKey = el.substring(index + 1);
        }
        if (source instanceof String) {
            if ("".equals(key)) {
                if (!"".equals(((String) source).trim())) {
                    source = crypt.exec((String) source);
                }
            } else {
                throw new RuntimeException("字段：" + el + "没有找到");
            }
        } else if (source instanceof Collection) {
            Collection c = (Collection)source;
            Object[] objects = c.toArray();
            c.clear();
            for (Object object : objects) {
                c.add(exec(object,el,crypt));
            }
        } else if (source.getClass().isArray()) {
            int length = Array.getLength(source);
            for (int i = 0; i < length; i++) {
                Array.set(source,i, exec(Array.get(source,i),el,crypt));
            }
        } else if (source instanceof Map) {
            Map m = (Map) source;
            Set set = m.keySet();
            for (Object k : set) {
                Object v = m.get(k);
                m.put(k, exec(v,el,crypt));
            }
        } else {
            Field field = ReflectUtil.getField(source.getClass(), key);
            if (field == null) {
                throw new RuntimeException("字段："+ key + "没有找到");
            }
            Object o = ReflectUtil.getFieldValue(source, field);
            if (o != null) {
                ReflectUtil.setFieldValue(source,field,exec(o,endKey,crypt));
            }
        }
        return source;
    }

    private static boolean isResult(String allEl){
        if (allEl.equals("result") || allEl.startsWith("result.")) {
            return true;
        } else {
            return false;
        }
    }
    private static boolean isNative(Object o){
        return clazzs.contains(o.getClass());
    }

    public static String encrypt(String s) {
        return encryptUtil.exec(s);
    }

    public static String decrypt(String s) {
        return decryptUtil.exec(s);
    }
}

interface Crypt{
    String exec(String s);
}

/**
 * 加密
 */
@Slf4j
class EncryptUtil implements Crypt {

    private String dataProtectionAesKey;

    public EncryptUtil(String dataProtectionAesKey) {
        this.dataProtectionAesKey = dataProtectionAesKey;
    }

    @Override
    public String exec(String s) {
        if ("N".equals(dataProtectionAesKey)) {
            return s;
        }

        try {
            return AESUtil.aesEncrypt(s, dataProtectionAesKey);
        } catch (Exception e) {
            log.warn("Crypt加密失败，" + s);
        }
        return s;
    }
}

/**
 * 解密
 */
@Slf4j
class DecryptUtil implements Crypt {

    private String dataProtectionAesKey;

    public DecryptUtil(String dataProtectionAesKey) {
        this.dataProtectionAesKey = dataProtectionAesKey;
    }

    @Override
    public String exec(String s) {
        if ("N".equals(dataProtectionAesKey)) {
            return s;
        }

        try {
            return AESUtil.aesDecrypt(s, dataProtectionAesKey);
        } catch (Exception e) {
            log.warn("Crypt解密失败，" + s);
        }
        return s;
    }
}

