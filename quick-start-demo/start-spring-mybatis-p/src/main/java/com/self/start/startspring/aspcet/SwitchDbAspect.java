package com.self.start.startspring.aspcet;


import com.self.start.startspring.config.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 描述:公共字段处理切面
 */
@Component
@Aspect
@Slf4j
public class SwitchDbAspect {

    @Pointcut("execution(* com..*controller..*.*(..))")
    private void cut() {
    }


    @Before(value = "cut()")
    public void before(JoinPoint joinPoint) {

        DataSourceContextHolder.setDBType("master");

    }


}
