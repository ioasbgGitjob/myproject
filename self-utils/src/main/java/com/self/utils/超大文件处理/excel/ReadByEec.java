package com.self.utils.超大文件处理.excel;

import cn.hutool.core.date.StopWatch;
import org.junit.Test;

/**
 * @author szy
 * @version 1.0
 * @description 使用eec读取超大excel
 * @date 2022-01-24 14:25:37
 */

public class ReadByEec {

    @Test
    public void t() throws Exception {
        StopWatch s = new StopWatch();
        s.start();
        // TODO:


        s.stop();
        System.out.println("------------------");
        System.out.println(s.getTotalTimeSeconds());

//        Thread.sleep(100000);
    }

    @Test
    public void read(){

    }

}
