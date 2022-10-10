package codewars;

import com.sun.deploy.util.StringUtils;
import com.sun.javafx.binding.StringFormatter;
import org.junit.Test;
import org.springframework.util.StopWatch;

/**
 * @author szy
 * @version 1.0
 * @description https://www.codewars.com/dashboard
 * @date 2021-12-13 18:14:06
 */

public class Kyu7 {

    /**
     * @题目 求 aeiou 在一个字符串中出现的次数
     * @知识点 replaceAll 表达式
     * 字符串长度 > 100万 方案 2
     * 字符串长度 < 100万 方案 1
     */
    @Test
    public void getCount() {
        StopWatch s = new StopWatch();
        StringBuffer strBu = new StringBuffer("aaaa");
        while (strBu.length() < 1000000) {
            strBu.append("daspoquweoipdzxhbnvmcxvpqowirfpuyeoqpwcnxzmbv,cbzwa's;opkljhkb ");
        }
        String str = strBu.toString();
        s.start("方案 1");
        //  (?i): 不区分大小写   [^aeiou] 匹配除了aeiou这几个字母以外的任意字符
        int count = str.replaceAll("(?i)[^aeiou]", "").length();
        s.stop();
        System.out.println("方案1:" + count);

        s.start("方案 2");
        count = (int) str.chars().filter(c -> "aeiou".indexOf(c) >= 0).count();
        s.stop();
        System.out.println("方案2:" + count);
        System.out.println(s.prettyPrint());
    }



}
