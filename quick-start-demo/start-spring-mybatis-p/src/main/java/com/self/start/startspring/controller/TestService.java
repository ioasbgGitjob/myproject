package com.self.start.startspring.controller;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.self.start.startspring.entity.Test;
import com.self.start.startspring.mapper.TestMapper;
import org.springframework.stereotype.Service;

/**
 * @author szy
 * @version 1.0
 * @date 2023-03-03 15:08:42
 * @description
 *
 */

@Service
public class TestService extends ServiceImpl<TestMapper, Test> {

}
