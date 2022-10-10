package com.ocl.toolkit;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author szy
 * @version 1.0
 * @description 测试类
 * @date 2021-11-11 17:40:03
 */

@Data
@Builder
public class User {
    private Long Id;

    private String name;
    private Integer age;
    private String address;
    private String password;
    private LocalDate birthday;
    private BigDecimal price;
    private Date create_time;
    private Date upd_time;

}
