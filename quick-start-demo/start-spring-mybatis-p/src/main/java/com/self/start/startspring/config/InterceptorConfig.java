package com.self.start.startspring.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author szy
 * @version 1.0
 * @date 2023-03-03 15:59:44
 * @description
 */

@Configuration
// 老版本呢是继承WebMvcConfigurerAdapter不过新版本已经放弃了，推荐用下面的方式。
public class InterceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // ** 表示所有拦截路径
//        registry.addInterceptor(aIntercepttor).addPathPatterns("/**");
        // 或下面这种写法  【若编写自定义拦截器类没有加@Component注解】
        registry.addInterceptor(new AIntercepttor()).addPathPatterns("/**");
    }
}