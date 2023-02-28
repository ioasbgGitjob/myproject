package com.self.start.startspring.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.ismail.mxreflection.annotations.Arg;
import com.ismail.mxreflection.annotations.Expression;
import com.ismail.mxreflection.core.Calculator;
import com.ismail.mxreflection.factory.MXFactory;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author szy
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

    @TableField(fill = FieldFill.DEFAULT)
    private Long age;


}
