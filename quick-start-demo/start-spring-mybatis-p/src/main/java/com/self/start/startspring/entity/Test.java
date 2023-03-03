package com.self.start.startspring.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author szy123
 * @version 1.0
 * @date 2023-02-21 11:06:27
 * @description
 */
@Data
//@TableName("test")
@Accessors(chain = true)
public class Test {

    @TableId(type = IdType.AUTO)
    private Long id;
    private String name;
    private LocalDateTime createTime;
    private Long age;
    private Long tenantId;



    public String getStr() {
//        return "我是 Test.class中的方法: getStr()";
        return null;
    }

}
