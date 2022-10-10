package com.example.self.aspect.dataProtection;

import java.lang.annotation.*;

/**
 * 数据保护，加解密工具注解
 * 使用须知：
 * 1、先执行加密后执行解密
 * 2、参数以#开始
 * 3、对返回结果操作，需要以#result开始
 * 4、支持java.util.Collection、java.util.Map、数组、除了基础类型和包装类的Object类型
 * 5、示例：
 * <pre>{@code
 * @RestController
 * @RequestMapping("/t")
 * public class TController {
 *
 *     @PostMapping(value = "/a")
 *     @DataProtection(encrypt = {
 *             "#users"
 *     },decrypt = {
 *             "#result"
 *     })
 *     public Object a(@RequestBody List<String> users){
 *         return users;
 *     }
 *
 *     @PostMapping(value = "/b")
 *     @DataProtection(encrypt = {
 *             "#users"
 *     },decrypt = {
 *             "#result"
 *     })
 *     public Object b(@RequestBody List<List<String>> users){
 *         return users;
 *     }
 *
 *     @PostMapping(value = "/c")
 *     @DataProtection(encrypt = {
 *             "#users"
 *     },decrypt = {
 *             "#result"
 *     })
 *     public Object c(@RequestBody List<Map<String,String>> users){
 *         return users;
 *     }
 *     @PostMapping(value = "/d")
 *     @DataProtection(encrypt = {
 *             "#users"
 *     },decrypt = {
 *             "#result"
 *     })
 *     public Object d(@RequestBody Map<String,List<String>> users){
 *         return users;
 *     }
 *
 *     @PostMapping(value = "/e")
 *     @DataProtection(encrypt = {
 *             "#user.name"
 *             ,"#user.list"
 *             ,"#user.friends.name"
 *             ,"#user.friends.friends.name"
 *     },decrypt = {
 *             "#result.name"
 *             ,"#result.list"
 *             ,"#result.friends.name"
 *             ,"#result.friends.friends.name"
 *     })
 *     public Object e(@RequestBody TUser user){
 *         return user;
 *     }
 *
 *     @PostMapping(value = "/f")
 *     @DataProtection(encrypt = {
 *             "#users.name"
 *             ,"#users.friends.name"
 *             ,"#users.friends.friends.name"
 *             ,"#users.address"
 *             ,"#users.friendMap.name"
 *     })
 *     public Object f(@RequestBody List<TUser> users){
 *         return users;
 *     }
 *
 *     @PostMapping(value = "/g")
 *     @DataProtection(encrypt = {
 *             "#a","#b"
 *     })
 *     public Object g(@RequestParam String a,@RequestParam String b){
 *         return a + "," + b;
 *     }
 * }
 * @Data
 * public class TUser {
 *     private Long id;
 *     private String name;
 *     private Integer age;
 *     private List<TFriend> friends;
 *     private Map<String,String> address;
 *     private Map<String,TFriend> friendMap;
 *     private List<String> list;
 * }
 * @Data
 * public class TFriend {
 *     private String name;
 *     private List<TFriend> friends;
 * }
 * }</pre>
 * {@link DataProtectionAspect}
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataProtection {
    String [] encrypt() default {}; // 加密
    String [] decrypt() default {}; // 解密
}
