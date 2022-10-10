package com.example.jdkdemo.jdk10;

import java.time.LocalDate;

/**
 * @author szy
 * @version 1.0
 * @description 关键字var 使用
 * @date 2022-06-06 16:02:40
 *
 *本地变量才能够使用var </br>
 *var在定义的时候就必须被初始化 </br>
 *它不能用在类变量的定义中，不能用在方法变量中，不能用在构造函数中，不能用在方法返回中,不能用在catch变量定义中 </br>
 *
 */
public class VarDemo {

    public static void main(String[] args) {

        var t1 = "我是字符串";
        var t2 = 10;
        var t3 = false;
        var t4 = new VarEntity();

        System.out.println(t1);
        System.out.println(t2);
        System.out.println(t3);
        System.out.println(t4);

    }

    static class VarEntity{
        String name;
        int age;
        LocalDate localDate;
    }

}
