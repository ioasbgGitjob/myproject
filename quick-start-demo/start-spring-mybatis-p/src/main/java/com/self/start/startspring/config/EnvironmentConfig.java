package com.self.start.startspring.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * 描述:获取spring env配置
 *
 * @auther: zengqing
 * @date: 2022/8/26 10:59
 */
@Configuration
public class EnvironmentConfig implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        this.environment = applicationContext.getEnvironment();
    }

    public static void setEnvironment(Environment environment1) {
        if (environment1 != null) {
            environment = environment1;
        }
    }

    /**
     * 描述:通过key获取env配置
     *
     * @auther: zengqing
     * @date: 2022/8/26 11:06
     */
    public static String getProperty(String key) {
        if (environment == null) {
            return null;
        }
        return environment.getProperty(key);
    }

    public static Object getBean(Class className) {
        return applicationContext.getBean(className.getName());
    }

    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

}
