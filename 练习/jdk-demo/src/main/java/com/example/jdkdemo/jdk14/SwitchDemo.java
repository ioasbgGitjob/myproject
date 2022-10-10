package com.example.jdkdemo.jdk14;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2022-06-07 14:31:10
 */

public class SwitchDemo {


    // 标准模式
    public static void t1() {
        int flag = 3;
        switch (flag) {
            case 0:
                System.out.println("The number is 0");
                break;
            case 1:
                System.out.println("The number is 1");
                break;
            case 2:
                System.out.println("The number is 2");
                break;
            default:
                System.out.println("you are right");
                break;
        }
    }

    // 使用 - > 方法 ， 不用使用 break 命令， 保证只有一种路径会被执行！
    public static void t2() {
        int flag = 3;
        switch (flag){
            case 0 -> System.out.println("The number is 0" );
            case 1 -> System.out.println("The number is 1" );
            case 2 -> System.out.println("The number is 2" );
            default -> System.out.println("you are right" );
        }
    }

    // yield  返回值
    public static void t3() {
        int flag = 2;
        String ss;
        ss = switch (flag){
            case 0 -> "The number is 0" ;
            case 1 -> "The number is 1" ;
            case 2 -> {
                String c = "The number is 2";
                yield c;
            }
            default -> "you are right" ;
        };
        System.out.println(ss);
    }


}
