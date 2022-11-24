package com.self.gatewaytest.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 测试方法：
 * 以不同端口启动两个实例，一个加分组字段, 一个不加
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Value("${server.port}")
    Integer port;

    @RequestMapping("/date")
    public String date() {
        return "当前日期: " + LocalDateTime.now() + ":port:" + port;
    }

    @RequestMapping("/ok")
    public String ok() {
        return "OK";
    }
}
