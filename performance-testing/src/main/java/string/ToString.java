package string;

import cn.hutool.core.date.StopWatch;

import java.util.ArrayList;
import java.util.List;

/**
 * @author szy
 * @version 1.0
 * @description String 隐式转换的性能 (jdk17)
 * t1: (String) 隐式转换,  10次平均耗时: 600 ±50毫秒  <br>
 * t1: +""  字符串拼接, 10次平均耗时:   1308 ±50    毫秒  <br>
 * t1: toString(), 10次平均耗时:       650 ±50 毫秒  (jdk8 只有70 ±10 ??? 怎么回事)<br>
 * 结论:
 * @date 2022-08-01 14:26:35
 */

public class ToString {
    // 循环的次数
    public static long TIMES = 10000000;

    public static void main(String[] args) {
        long l1 = 0; // 平均耗时
        int ii = 10; // 测试的次数
        for (int i = 0; i < ii; i++) {
            StopWatch s = new StopWatch();
            s.start();
            t1();
//            t2();
//            t3();
            s.stop();
            l1 += s.getTotalTimeMillis();
            System.out.println(s.getTotalTimeMillis());
        }
        System.out.println("循环" + ii + "遍");
        System.out.println("平均用时:" + l1 / ii);
    }

    /**
     * (String) 隐式转换
     */
    static void t1() {
//        Object o = "阿三大啊大撒法" + Math.random();
        Object o = "阿三大啊大撒法";
        List<String> s = new ArrayList<>();
        for (int i = 0; i < TIMES; i++) {
            s.add((String) o);
        }
    }

    /**
     * +"" 拼接字符串转换
     */
    static void t2() {
        Object o = "阿三大啊大撒法" + Math.random();
        List<String> s = new ArrayList<>();
        for (int i = 0; i < TIMES; i++) {
            s.add(o + "");
        }
    }

    static void t3() {
        Object o = "阿三大啊大撒法" + Math.random();
        List<String> s = new ArrayList<>();
        for (int i = 0; i < TIMES; i++) {
            s.add(o.toString());
        }
    }
}
