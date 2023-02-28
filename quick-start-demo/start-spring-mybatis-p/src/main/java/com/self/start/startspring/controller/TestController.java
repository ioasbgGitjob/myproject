package com.self.start.startspring.controller;

import com.self.start.startspring.entity.Test;
import com.self.start.startspring.mapper.TestMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @author szy
 * @version 1.0
 * @date 2023-02-21 11:12:15
 * @description
 */

@RestController
public class TestController {


   private  final TestMapper testMapper;

    public TestController(TestMapper testMapper) {
        this.testMapper = testMapper;
    }


    @GetMapping("/getAll")
    public List<Test> getAll() {
//        return testMapper.getAll();
         List<Test> all = testMapper.selectList(null );

//        Test t = new Test();
//        t.setName("ssss");
//        t.setCreateTime(LocalDateTime.now());
//        int ss = testMapper.insert(t);
//        System.out.println(ss);
//        System.out.println("id:"+t.getId());
//        return testMapper.selectList(new QueryWrapper<Test>().lambda()
//                .eq(Test::getId, 61)
//                .or().in(Test::getId, 1, 2, 3, 61, 63));
        Test aa = testMapper.getById(61L);
        return Arrays.asList(aa);
    }

    @GetMapping("/getAlll")
    public List<Test> getAlll(){
        return testMapper.getAll();
    }

}
