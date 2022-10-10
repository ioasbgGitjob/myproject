package com.self.utils;

import com.self.utils.发邮件.SpringbootMailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UtilsApplicationTests {

    @Test
    void contextLoads() {
    }


    @Autowired
    SpringbootMailUtil springbootMailUtil;

    @Test

    void springbootMail() {
        springbootMailUtil.simple();
    }

}
