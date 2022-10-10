package com.example.self.aspect.dataProtectMoan;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author szy
 * @version 1.0
 * @date 2022-10-09 14:23:00
 * @description 加解密切面
 * 加密: 方法入参类型支持: String, 普通对象
 * 解密: 方法返回结果类型支持 : 普通单曾对象, List, String
 * <p>
 * 要求:
 * 被接加密的对象 需要保证有对称的属性: [***Id <--> ***Key],[id <--> key]
 * <p>
 * 缺陷: 只处理一级对象
 * <p>
 * 可升级:
 * 现在是指定 加解密对象的属性, 自动匹配 加解密完成后要存入的对象,
 * 可以考虑的升级方案:
 * 1. 再定义一个注解, 以 map数据代替string数组,
 * 2. 直接在使用注解的时候, 指定加解密对象的存储属性
 */

@Aspect
@Component
public class DESAspect {

    private static Logger logger = LoggerFactory.getLogger(DESAspect.class);

    @Pointcut("@annotation(com.example.self.aspect.dataProtectMoan.DataProtection)")
    public void dataProtectionCut() {

    }

    @Around("dataProtectionCut()")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object proceed;
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上的注解
        DataProtection myAnnotation = method.getAnnotation(DataProtection.class);

//        String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method); // 获取所有参数的名字

        Object[] args = joinPoint.getArgs();
        try {
            // 执行解密操作
            doDecrypt4Str(args, method);
            doDecrypt(args, myAnnotation);
            // 执行解密操作
            proceed = joinPoint.proceed(args);
            // 执行加密操作
            doEncrypt(proceed, myAnnotation);
        } catch (Throwable e) {
            logger.error("加解密异常", e);
            throw new RuntimeException(e);
        }
        return proceed;

    }

    /**
     * 解密字符串类型
     *
     * @param args
     * @param method
     */
    private void doDecrypt4Str(Object[] args, Method method) {
        List<String> keys = new ArrayList<>();
        for (Parameter parameter : method.getParameters()) {
            DectyptToId annotion = parameter.getAnnotation(DectyptToId.class);
            if (null != annotion) {
                keys.add(parameter.getName());
            }
        }
        if (keys.size() == 0) {
            return;
        }

        String[] parameterNames = new DefaultParameterNameDiscoverer().getParameterNames(method); // 获取所有参数的名字
        for (int i = 0; i < parameterNames.length; i++) {
            if (keys.contains(parameterNames[i])) {
                // TODO 解密
                args[i] = "解密后的值";
            }
        }
    }

    private void doEncrypt(Object proceed, DataProtection myAnnotation) throws IllegalAccessException {
        if (null == proceed) return;
        String[] fieldNames = myAnnotation.encrypt();
        if (null == fieldNames || fieldNames.length == 0) return;
        /**
         * 默认的转换关系 key:已加密的属性的名字, value:待解密的属性的名字
         * @example:
         * key = userKey, --> value = userId
         * userKey = KJHGASUIG&^^%&DFF,  --> userId = 10
         */
        // 升级思路: 可以考虑key,value 都让用户来输入
        Map<String, String> fieldNameMap = Arrays.asList(fieldNames).stream().collect(Collectors.toMap(key -> key, key -> {
            if (key.equals("id")) {
                return "key";
            } else if (key.lastIndexOf("id") == key.length() - 2) {
                return key.replace("Id", "Key");
            } else {
                return "";
            }
        }));
        execResult(proceed, fieldNameMap);
    }

    private Object execResult(Object proceed, Map<String, String> fieldNameMap) throws IllegalAccessException {
        if (proceed instanceof String) {
            proceed = "已经被加密啦";
        } else if (proceed instanceof Collection) {
            Collection c = (Collection) proceed;
            for (Object object : c) {
                execResult(object, fieldNameMap);
            }
        } else {
            // 真正的处理逻辑
            Map<String, Field> fields = Arrays.stream(proceed.getClass().getDeclaredFields()).collect(Collectors.toMap(f -> f.getName(), Function.identity()));
            for (String decryptField : fieldNameMap.keySet()) {
                Field f = fields.get(decryptField);
                if (null == f) return proceed;
                f.setAccessible(true);

                Field f2 = fields.get(fieldNameMap.get(decryptField));// 要转换的值
                if (null == f2) return proceed;
                f2.setAccessible(true);

                Object value = f.get(proceed);
                if (null == value) return proceed;
                // TODO: 这里执行加密操作
                f2.set(proceed, "999"); // 先用999测试, 表示已经解密成功
            }
        }
        return proceed;
    }

    /**
     * 执行解密操作 </br>
     * 目前只支持解密后的属性为: Long,Integer
     *
     * @param args
     * @param myAnnotation
     * @throws IllegalAccessException
     */
    private void doDecrypt(Object[] args, DataProtection myAnnotation) throws IllegalAccessException {
        // 拿到注解里 [decrypt] 的值(需要解密的字段)
        String[] fieldNames = myAnnotation.decrypt();
        if (null == fieldNames || fieldNames.length == 0) return;

        /**
         * 默认的转换关系 key:已加密的属性的名字, value:待解密的属性的名字
         * @example:
         * key = userKey, --> value = userId
         * userKey = KJHGASUIG&^^%&DFF,  --> userId = 10
         */
        // 升级思路: 可以考虑key,value 都让用户来输入
        Map<String, String> fieldNameMap = Arrays.asList(fieldNames).stream().collect(Collectors.toMap(key -> key, key -> {
            if (key.equals("key")) {
                return "id";
            } else if (key.lastIndexOf("Key") == key.length() - 3) {
                return key.replace("Key", "Id");
            } else {
                return "";
            }
        }));

        for (Object arg : args) {
            if (arg instanceof String) {
                // string 类型在上边已经处理过了
            } else {
                Map<String, Field> fields = Arrays.stream(arg.getClass().getDeclaredFields()).collect(Collectors.toMap(f -> f.getName(), Function.identity()));
                for (String decryptField : fieldNameMap.keySet()) {
                    Field f = fields.get(decryptField);
                    if (null == f) continue;
                    f.setAccessible(true);

                    Field f2 = fields.get(fieldNameMap.get(decryptField));// 要转换的值
                    if (null == f2) continue;
                    f2.setAccessible(true);

                    Object value = f.get(arg);
                    if (null == value || value.toString().trim().length() == 0) continue;
                    // TODO: 这里执行解密操作
                    String f2ClassType = f2.getClass().getTypeName().getClass().getTypeName();
                    if (f2ClassType.equals(Long.class.getTypeName())) {
                        f2.set(arg, 999L); // 先用999测试, 表示已经解密成功
                    }
                    if (f2ClassType.equals(Integer.class.getTypeName())) {
                        f2.set(arg, 999); // 先用999测试, 表示已经解密成功
                    }
                }
            }

        }
    }

}
