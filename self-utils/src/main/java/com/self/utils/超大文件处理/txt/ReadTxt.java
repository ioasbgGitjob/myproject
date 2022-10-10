package com.self.utils.超大文件处理.txt;

import org.junit.Test;
import org.springframework.util.StopWatch;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author szy
 * @version 1.0
 * @description 读取超大文本
 * @date 2021-12-27 15:56:14
 */

public class ReadTxt {

    final String TXT_PATH = "D:\\tmp\\big_txt.txt";

    @Test
    public void readMain() throws Exception {
        StopWatch s = new StopWatch();
        s.start("readByNio");
        readByFileChannel();
        s.stop();

        System.out.println("总用时" + s.getTotalTimeSeconds());
        System.out.println(s.prettyPrint());

        System.out.println("执行完成啦啦啦啦啦啦啦啦啦啦啦啦!");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * nio  1.7G 只需要4秒
     */
    @Test
    public void readByFileChannel() { // 最推荐
        File file = new File(TXT_PATH);

        try (
                FileInputStream fileInputStream = new FileInputStream(file);
                FileChannel fileChannel = fileInputStream.getChannel();
                FileWriter fileWriter = new FileWriter(new File("D:\\tmp\\big_txt1.txt").getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fileWriter)
        ) {
            int capacity = 1 * 1024 * 1024;//1M
            ByteBuffer byteBuffer = ByteBuffer.allocate(capacity);
            StringBuffer buffer = new StringBuffer();
            while (fileChannel.read(byteBuffer) != -1) {
                //读取后，将位置置为0，将limit置为容量, 以备下次读入到字节缓冲中，从0开始存储
                byteBuffer.clear();
                byte[] bytes = byteBuffer.array();
                String str = new String(bytes);
                //处理字符串,并不会将字符串保存真正保存到内存中
                // 这里简单模拟下处理操作.
                buffer.append(str.substring(0, 1));
                // 写入操作
//                bw.write(str);
//                bw.newLine();
            }
            System.out.println("buffer.length:" + buffer.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
