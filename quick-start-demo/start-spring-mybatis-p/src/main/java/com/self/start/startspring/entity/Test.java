package com.self.start.startspring.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("test")
public class Test {

//    @TableId(type = IdType.INPUT)

    private Long id;
    private String name;
    // 插入和更新时都会填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime createTime;
    private Long age;
    private Long tenantId;


    public String getStr() {
//        return "我是 Test.class中的方法: getStr()";
        return null;
    }

}
