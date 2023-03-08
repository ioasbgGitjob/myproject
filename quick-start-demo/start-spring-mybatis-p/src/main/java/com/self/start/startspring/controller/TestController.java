package com.self.start.startspring.controller;

import com.self.start.startspring.entity.Test;
import com.self.start.startspring.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author szy123
 * @version 1.0
 * @date 2023-02-21 11:12:15
 * @description
 */
//@Transactional
@RestController
public class TestController {


    private final TestMapper testMapper;
    private final TestService testService;

    @Autowired
    public TestController(TestMapper testMapper, TestService testService) {
        this.testMapper = testMapper;
        this.testService = testService;
    }


    @GetMapping("/getAll")
    public List<Test> getAll()   {
        Test test1 = new Test();
        test1.setAge(999L);
        testMapper.insert(test1);
        Test aa = testMapper.getById(61L);
        return testMapper.selectList(null);
    }

    @GetMapping("/getAlll")
    public List<Test> getAlll() {
        return testMapper.getAll();
    }

}
