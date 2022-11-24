package com.self.gatewaytest.controller;

import com.self.gatewaytest.ClientTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试方法：
 * 以不同端口启动两个实例，一个加分组字段, 一个不加
 */
@RestController
@RequestMapping("/feign")
public class TestController {

    @Autowired
    ClientTest clientTest;

    @RequestMapping("/date")
    public String date() {
        return clientTest.date();
    }

}
