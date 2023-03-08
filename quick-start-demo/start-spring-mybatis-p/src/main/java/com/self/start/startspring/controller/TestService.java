package com.self.start.startspring.controller;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.start.startspring.entity.Test;
import com.self.start.startspring.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;

/**
 * @author szy
 * @version 1.0
 * @date 2023-03-03 15:08:42
 * @description
 */


@Service
public class TestService extends ServiceImpl<TestMapper, Test> {


    private final TestMapper testMapper;

    @Autowired
    public TestService(TestMapper testMapper) {
        this.testMapper = testMapper;
    }


    @Transactional(rollbackFor = Exception.class)
    public void testTransaction() {
        Test t = new Test();
        t.setName("ssss");
        t.setCreateTime(LocalDateTime.now());
        testMapper.insert(t);

        extracted();
    }

    public void extracted() {
        try {
            System.out.println(Long.valueOf("asdc"));
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            throw new RuntimeException("12");
        }
    }


}
