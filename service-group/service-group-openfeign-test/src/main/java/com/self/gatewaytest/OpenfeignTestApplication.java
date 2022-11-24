package com.self.gatewaytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@EnableDiscoveryClient
@EnableFeignClients
public class OpenfeignTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(OpenfeignTestApplication.class, args);
    }
}
