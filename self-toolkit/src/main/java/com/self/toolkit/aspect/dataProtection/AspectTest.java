package com.self.toolkit.aspect.dataProtection;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据保护，加解密工具注解
 * 使用须知：
 * 1、先执行加密后执行解密
 * 2、参数以#开始
 * 3、对返回结果操作，需要以#result开始
 * 4、支持java.util.Collection、java.util.Map、数组、除了基础类型和包装类的Object类型
 * 5、示例：
 **/

@Component
public class AspectTest {
    @DataProtection(encrypt = {
            "#users"
    })
    public Object a(List<String> users) {
        return users;
    }

    @DataProtection(encrypt = {
            "#users"
    }, decrypt = {
            "#result"
    })
    public Object b(List<List<String>> users) {
        return users;
    }

    @DataProtection(encrypt = {
            "#users"
    }, decrypt = {
            "#result"
    })
    public Object c(List<Map<String, String>> users) {
        return users;
    }

    @DataProtection(encrypt = {
            "#users"
    }, decrypt = {
            "#result"
    })
    public Object d(Map<String, List<String>> users) {
        return users;
    }

    @DataProtection(encrypt = {
            "#user.name"
            , "#user.list"
            , "#user.friends.name"
            , "#user.friends.friends.name"
    }
//    , decrypt = {
//            "#result.name"
//            , "#result.list"
//            , "#result.friends.name"
//            , "#result.friends.friends.name"
//    }
    )
    public Object e(TUser user) {
        return user;
    }

    @DataProtection(encrypt = {
            "#users.name"
            , "#users.friends.name"
            , "#users.friends.friends.name"
            , "#users.address"
            , "#users.friendMap.name"
    })
    public Object f(List<TUser> users) {
        return users;
    }
    @DataProtection(encrypt = {
            "#users.name"
    })
    public Object ff(List<TUser> users) {
        return users;
    }

    @DataProtection(encrypt = {
            "#a", "#b"
    })
    public Object g(String a, String b) {
        return a + "," + b;
    }

    @Data
    public static class TUser {
        private Long id;
        private String name;
        private Integer age;
        private List<TFriend> friends;
        private Map<String, String> address;
        private Map<String, TFriend> friendMap;
        private List<String> list;
    }

    @Data
    public class TFriend {
        private String name;
        private List<TFriend> friends;
    }
}
