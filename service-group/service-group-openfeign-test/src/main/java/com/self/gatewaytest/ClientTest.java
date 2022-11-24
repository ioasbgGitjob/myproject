package com.self.gatewaytest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "test")
public interface ClientTest {
    @GetMapping("/test/date")
    String date();

    @GetMapping("/test/callSpringCloudRecursively")
    String callSpringCloudRecursively(@RequestParam("level") Integer level);
}
