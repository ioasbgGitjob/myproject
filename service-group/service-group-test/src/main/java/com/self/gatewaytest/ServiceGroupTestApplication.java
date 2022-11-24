package com.self.gatewaytest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@EnableDiscoveryClient
public class ServiceGroupTestApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(ServiceGroupTestApplication.class, args);
    }
}
