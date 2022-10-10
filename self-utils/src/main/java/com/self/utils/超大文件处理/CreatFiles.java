package com.self.utils.超大文件处理;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-12-27 15:56:57
 */

public class CreatFiles {

    @Test
    public void main() {
        StopWatch s = new StopWatch();
        txt(s);
        System.out.println("总用时" + s.getTotalTimeSeconds());
        System.out.println(s.prettyPrint());

        System.out.println("执行完成啦啦啦啦啦啦啦啦啦啦啦啦!");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    @Test
    public void txt(StopWatch s) {
        s.start("生成txt");
        String filePath = "D:\\tmp\\big_txt.txt";
        File file = new File(filePath);
        FileWriter fw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            for (int i = 0; i < 10000000; i++) {
                bw.write("哈数据库的建行卡收到后尽快多萨和就开始啥叫客户的撒娇客户记号记号卡萨丁和艰苦撒旦将很快安徽科技的撒娇好看的JKHKJKHGKJHGFJKKHJHKHG");
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        s.stop();
    }


}
