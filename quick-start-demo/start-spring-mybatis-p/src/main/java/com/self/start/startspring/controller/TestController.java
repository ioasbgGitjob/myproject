package com.self.start.startspring.controller;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.self.start.startspring.entity.Test;
import com.self.start.startspring.mapper.TestMapper;
import com.self.start.startspring.mapper.UserMapper;
import org.sca.arch.application.common.util.SystemContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
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


   private  final TestMapper testMapper;
   private  final UserMapper userMapper;
   private  final TestService testService;

   @Autowired
    public TestController(TestMapper testMapper, UserMapper userMapper, TestService testService) {
        this.testMapper = testMapper;
        this.userMapper = userMapper;
       this.testService = testService;
   }


    @GetMapping("/getAll")
    public List<Test> getAll() throws SQLException {
        Test t = new Test();
        t.setName("ssss");
        t.setCreateTime(LocalDateTime.now());
        int ss = testMapper.insert(t);

         List<Test> all = all = testMapper.getAll();

        all = testMapper.selectList(null );



        Test test1 = all.get(0);
        test1.setAge(999L);
        testMapper.updateById(test1);

        testMapper.deleteById(test1);

        all = testService.query().list();


//        System.out.println(ss);
//        System.out.println("id:"+t.getId());
//        return testMapper.selectList(new QueryWrapper<Test>().lambda()
//                .eq(Test::getId, 61)
//                .or().in(Test::getId, 1, 2, 3, 61, 63));
        for (Test test : all) {
            test.setAge(999L);
        }
        testService.saveOrUpdateBatch(all, 10);
        Test aa = testMapper.getById(61L);
        return Arrays.asList(aa);
    }

    @GetMapping("/getAlll")
    public List<Test> getAlll(){
        return testMapper.getAll();
    }

}
