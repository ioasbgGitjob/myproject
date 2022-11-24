package com.self.servicegroup.configuration;

import feign.RequestInterceptor;
import org.sca.arch.microservice.config.MetaDataConfig;
import org.sca.arch.microservice.interceptor.FeignRequestInterceptor;
import org.sca.arch.microservice.rule.ServerIpChecker;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(MetaDataConfig.class)
public class MicroserviceAutoConfiguration {

    @Bean
    public MetaDataConfig metaDataConfig() {
        MetaDataConfig metaDataConfig = new MetaDataConfig();
        return metaDataConfig;
    }

    @Bean
    public ServerIpChecker getGatewayServerIpChecker() {
        return new ServerIpChecker();
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return new FeignRequestInterceptor();
    }

}
