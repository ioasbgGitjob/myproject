package com.self.gatewaytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@EnableDiscoveryClient
//@EnableFeignClients({"com.moan.test"})
public class GatewayTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(GatewayTestApplication.class, args);
    }
}
