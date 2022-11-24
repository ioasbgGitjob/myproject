package com.self.servicegroup.configuration;

import org.sca.arch.microservice.interceptor.SpringCloudInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MicroserviceNormalAutoConfiguration extends MicroserviceAutoConfiguration implements WebMvcConfigurer {

    @Bean
    public SpringCloudInterceptor getHttpTraceInterceptor() {
        return new SpringCloudInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SpringCloudInterceptor())
                .addPathPatterns("/**");
    }

}
