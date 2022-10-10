package com.example.jdkdemo.jdk13;

/**
 * @author szy
 * @version 1.0
 * @description 文本块
 * @date 2022-06-07 14:37:01
 */

public class TextBlockDemo {

    public static void main(String[] args) {

        // 老的写法
        String oldStr =
                "<html>\n" +
                        "<body>\n" +
                        "  <h1>Java 13 新特性：文本块</h1>\n" +
                        "</body>\n" +
                        "</html>\n";
        System.out.println("老的写法:" + oldStr);

        // 新的写法
        String newStr = """
                <html>
                <body>
                <h1>Java 13 新特性：文本块</h1>
                </body>
                </html>
                """;
        System.out.println("新的写法:" + newStr);
        // 新写法  转义符  不换行 \
        String newStr1 = """
                123\
                456
                """;
        // 上边相当于123456
        System.out.println("新的写法- 换行符号\\:" + newStr1);


    }

}
