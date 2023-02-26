package com.example.complexcalculate.service;

import com.example.complexcalculate.entity.ExampleCalculate;
import com.ismail.mxreflection.annotations.Expression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-26 19:45:44
 * @description 修改计算公式
 */

@Slf4j
@Service
public class ChangeFormulaService {

    // 从配置文件里读取的值, 当然也可以从缓存, 数据库, 页面中获取
    @Value("${calculate.formula}")
    String newValue;

    private final ApplicationContext context;

    @Autowired
    public ChangeFormulaService(ApplicationContext context) {
        this.context = context;
    }

    /**
     * 这里高版本的jdk有个坑, 需要在启动参数里加上: --add-opens java.base/sun.reflect.annotation=ALL-UNNAMED
     *
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Class<?> updateAnnotationValue(Class<?> classes) throws NoSuchFieldException, IllegalAccessException {
        // 这种方式适用于单例, 不需要入参
//        ExampleCalculate myBean = context.getBean(ExampleCalculate.class);
//        Field displayName = myBean.getClass().getDeclaredField("field3");

        //反射找到字段: field3
        Field displayName = classes.getDeclaredField("field3");

        displayName.setAccessible(true);// 这里不改 下边改不了
        //获取字段上的注解
        Expression annotation = displayName.getAnnotation(Expression.class);
        InvocationHandler h = Proxy.getInvocationHandler(annotation);
        // 获取 AnnotationInvocationHandler 的 memberValues 字段
        Field hField;
        hField = h.getClass().getDeclaredField("memberValues");//不用改
        // 因为这个字段是 private final 修饰，所以要打开权限
        hField.setAccessible(true);
        // 获取 memberValues
        Map memberValues = (Map) hField.get(h);
        // 修改 value 属性值
        memberValues.put("value", newValue);
        log.info("修改公式完成:" + newValue);
        return classes;
    }
}
