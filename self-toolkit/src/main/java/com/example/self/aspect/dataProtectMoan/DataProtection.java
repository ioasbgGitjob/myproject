package com.example.self.aspect.dataProtectMoan;

import java.lang.annotation.*;

/**
 * @author szy
 * @version 1.0
 * @date 2022-10-09 14:19:52
 * @description 加解密切点
 *
 * @example:
 *
 *
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataProtection {

    /**
     * 解密
     *
     * @example: @DataProtection(decrypt = {"要解密的属性","要解密的属性"})
     */
    String[] decrypt() default "";

    /**
     * 加密
     */
    String[] encrypt() default "";

}
