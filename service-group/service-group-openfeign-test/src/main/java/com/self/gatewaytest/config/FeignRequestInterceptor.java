package com.self.gatewaytest.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * 拦截Feign调用，传递服务分组信息。
 */
@Configuration
public class FeignRequestInterceptor implements RequestInterceptor {


    @Override
    public void apply(RequestTemplate requestTemplate) {
        System.out.println(requestTemplate);

    }
}
