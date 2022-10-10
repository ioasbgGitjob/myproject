package com.example.self.aspect.dataProtectMoan;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping
public class TController {

    @GetMapping("/t1")
    @DataProtection
    public String t1(@DectyptToId String userKey, String otherInfo) {
        System.out.println("根据注解@DectyptToId 解密指定的字段");
        System.out.println("已被解密:userKey=" + userKey);
        return userKey;
    }

    @GetMapping("/t2")
    @DataProtection(decrypt = {"key", "userKey"})
    public T1 t2(T1 t2) {
        System.out.println("把入参的t1对象中的 key, userKey 分别解密为: id,userId");
        System.out.println("t2对象已被解密:" + t2);
        System.out.println("解密后的值类型支持: Integer, Long");
        return t2;
    }

    @GetMapping("/t3")
    @DataProtection(encrypt = {"id", "userId"})
    public T1 t3() {
        System.out.println("对返回结果进行加密");
        System.out.println("id --> key , userId --> userKey");
        T1 t3 = new T1();
        t3.setId(10L);
        return t3;
    }

    @GetMapping("/t4")
    @DataProtection(encrypt = {"id", "userId"})
    public List<T1> t4() {
        System.out.println("对返回结果进行加密: 支持List");
        System.out.println("id --> key , userId --> userKey");
        T1 t4 = new T1();
        t4.setId(10L);
        return Arrays.asList(t4, t4);
    }

    @GetMapping("/t5")
    @DataProtection(
            encrypt = {"id", "userId"},
            decrypt = {"key", "userKey"}
    )
    public List<T1> t5(T1 t1, @DectyptToId String addrssKey, String other) {
        System.out.println("终极大杂烩");
        System.out.println("解密的对象为: key  userKey");
        System.out.println("被解密过的入参T1: " + t1.toString());
        System.out.println("被解密过的入参addrssKey: " + t1);
        System.out.println("加密过的对象: 查看网页");
        T1 t4 = new T1();
        t4.setId(10L);
        return Arrays.asList(t4, t4);
    }

    @Data
    public class T1 implements Serializable {

        private Long id;
        private String key;

        private Integer userId;
        private String userKey;

    }

}
