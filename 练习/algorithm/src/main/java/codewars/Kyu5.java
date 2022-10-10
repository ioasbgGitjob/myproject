package codewars;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author szy
 * @version 1.0
 * @description https://www.codewars.com/dashboard
 * @date 2021-12-22 18:20:45
 */

public class Kyu5 {

    /**
     * @题目 给定一个整数n , 求  k^m = n中, k,m的任意一个组合 , 其中 k,m>1
     * @知识点
     */
    @Test
    public void isPerfectPower() {
        int n = 8;
        int[] result = null;
        for (int i = 2; ; i++) {
            int root = (int) Math.round(Math.pow(n, 1.0 / i));
            if (root < 2) break;
            if (Math.pow(root, i) == n) result = new int[]{root, i};
        }
        if (null != result) {
            Arrays.stream(result).forEach(System.out::println);
        }

    }

}


