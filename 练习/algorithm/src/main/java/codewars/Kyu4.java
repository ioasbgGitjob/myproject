package codewars;

import org.junit.Test;

/**
 * @author szy
 * @version 1.0
 * @description https://www.codewars.com/dashboard
 * @date 2021-12-22 18:20:45
 */

public class Kyu4 {

    /**
     * n² = a²+b²+c²+...
     * @题目 求: a,b,c... 值的所有可能性中, 最后一个值最大的那个组合,
     * 例如: n=11, 11² = 1² + 2² + 4² + 10² = 2²+ 6²+ 9² , return: 1,2,4,10 而不是2,6,9
     * @知识点  递归算法
     */
    @Test
    public String decompose(long n) {
        String l = tryDecomp(n * n, (long)Math.sqrt(n * n - 1));
        return l != null ? l.trim() : l;
    }

    private String tryDecomp(long nb, long rac) {
        if (nb == 0) return "";
        String l = null;
        long i = rac;
        while (i >= (long)Math.sqrt(nb/2.0) + 1) {
            long diff = nb - i * i;
            rac = (long)Math.sqrt(diff);
            l = tryDecomp(diff, rac);
            if (l != null) { return l + " " + i; }
            i -=1;
        }
        return null;
    }
}


