package 文件流;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * input
 * 导入txt格式的文件
 */
public class Input4txt {


    public static void main(String[] args) throws Exception {
        String path = "D:\\temp\\1.txt";

        Thread.sleep(2000);
        test1(path);
        Thread.sleep(2000);
        test2(path);
    }

    /**
     * 生成文件
     */
    public void getFile() throws IOException {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < 100001; i++) {
            list.add("1,2,3,4,5撒打算，打到，，撒大声地");
        }
        BufferedWriter bufferedWriter = Files.newBufferedWriter(Paths.get("D:\\temp\\1.txt"));
        for (String s : list) {
            bufferedWriter.write(s + "\n");
        }
    }

    /**
     * 读取文件
     * @param filepath  文件地址
     */
    public static void getFileTxt(String filepath) throws IOException {
        /**NIO 读取文件*/
//        String filepath = "D:\\temp\\1.txt";
        Path path = Paths.get(filepath);
        List<String> aa = Files.readAllLines(path);

        /**java8读取文件*/
        Stream<String> stringStream = Files
                .lines(Paths.get(filepath), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")));

        List<String> words = stringStream.collect(Collectors.toList());
        System.out.println("words===>" + words);

    }

    public static void test1(String filepath) throws Exception {
        String memo = "NIO 读取文件";
        Long l1 = System.currentTimeMillis();
        // TODO
        Path path = Paths.get(filepath);
        List<String> aa = Files.readAllLines(path);
        System.out.println(aa.size());
        System.out.println("-----------" + memo + "用时" + (System.currentTimeMillis() - l1));
    }

    public static void test2(String filepath) throws Exception {
        String memo = "java8读取文件";
        Long l1 = System.currentTimeMillis();
        // TODO
        /**java8读取文件*/
        List<String> words = Files
                .lines(Paths.get(filepath), Charset.defaultCharset()).collect(Collectors.toList());
//                .flatMap(line -> Arrays.stream(line.split(" ")));
        System.out.println(words.size());
//        System.out.println("words===>" + words);
        System.out.println("-----------" + memo + "用时" + (System.currentTimeMillis() - l1));
    }

}
