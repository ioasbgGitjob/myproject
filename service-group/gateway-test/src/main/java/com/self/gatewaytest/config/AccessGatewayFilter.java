package com.self.gatewaytest.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author szy
 * @version 1.0
 * @date 2022-11-06 14:56:54
 * @description
 */
@Configuration
@Slf4j
public class AccessGatewayFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        log.info("获取request:" + request);
        ServerHttpResponse respond = exchange.getResponse();
        log.info("获取respond:" + respond);
        // TODO 登录鉴权等操作
        log.info("TODO: gateway鉴权:");
        ServerHttpRequest r = request.mutate().header("test", "测试").build();
//        exchange.mutate().request(r).build();
        log.info("往header设置新的值");
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }

}
