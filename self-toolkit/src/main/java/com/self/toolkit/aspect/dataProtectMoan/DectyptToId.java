package com.self.toolkit.aspect.dataProtectMoan;

import java.lang.annotation.*;

/**
 * @author szy
 * @version 1.0
 * @date 2022-10-10 15:32:56
 * @description 标记注解
 */

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DectyptToId {

}
