package com.self.utils.字段格式转换;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author szy
 * @version 1.0
 * @description 数据库字段和entity属性的格式互转
 * @date 2021-11-30 16:00:01
 */

public class DBConvert {

    /**
     * 驼峰转下划线 (覆盖源文件)
     */
    public static void to_db(String inPath) {
        try {
            writeFile(inPath, toDbLower(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全小写):成功! 文件地址:" + inPath);
    }

    /**
     * 驼峰转下划线
     */
    public static void to_db(String inPath, String outPath) {
        try {
            writeFile(outPath, toDbLower(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全小写):成功! 文件地址:" + outPath);
    }

    /**
     * 驼峰转下划线(覆盖源文件)
     */
    public static void to_DB(String inPath) {
        try {
            writeFile(inPath, toDbUpper(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全大写):成功! 文件地址:" + inPath);
    }

    /**
     * 下划线转驼峰(覆盖源文件)
     */
    public static void to_Entity(String inPath) {
        try {
            writeFile(inPath, toEneity(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全大写):成功! 文件地址:" + inPath);
    }

    /**
     * 下划线转驼峰
     */
    public static void to_Entity(String inPath, String outPath) {
        try {
            writeFile(outPath, toEneity(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全大写):成功! 文件地址:" + outPath);
    }

    /**
     * 驼峰转下划线
     */
    public static void to_DB(String inPath, String outPath) {
        try {
            writeFile(outPath, toDbUpper(getFile(inPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("转换为数据库字段(全大写):成功! 文件地址:" + outPath);
    }


    private static List<String> toDbLower(List<String> list) {
        List<String> rs = new ArrayList<>();
        list.stream().forEach(e -> {
            String s = e;
            char[] c = s.toCharArray();
            StringBuffer buff = new StringBuffer();
            for (char c1 : c) {
                if (Character.isUpperCase(c1)) {
                    buff.append("_");
                    c1 = Character.toLowerCase(c1);
                }
                buff.append(c1);
            }
            rs.add(s);
            rs.add("[convert:" + buff + "]");
        });
        return rs;
    }

    private static List<String> toDbUpper(List<String> list) {
        List<String> rs = new ArrayList<>();
        list.stream().forEach(e -> {
            String s = e;
            char[] c = s.toCharArray();
            StringBuffer buff = new StringBuffer();
            for (char c1 : c) {
                if (Character.isUpperCase(c1)) {
                    buff.append("_");
                    c1 = Character.toLowerCase(c1);
                }
                buff.append(c1);
            }
            s = s + "[convert:" + buff + "]";
            rs.add(s);
            rs.add(("[convert:" + buff + "]").toUpperCase(Locale.ROOT));
        });
        return rs;
    }

    private static List<String> toEneity(List<String> list) {
        List<String> rs = new ArrayList<>();
        list.stream().forEach(e -> {
            String s = e;
            char[] c = s.toCharArray();
            StringBuffer buff = new StringBuffer();
            boolean skip = false;
            for (char c1 : c) {
                if (c1 == '_') {
                    skip = true;
                    continue;
                }
                if (skip) {
                    buff.append(Character.toUpperCase(c1));
                    skip = false;
                } else {
                    buff.append(Character.toLowerCase(c1));
                }
            }
            rs.add(s);
            rs.add("[convert:" + buff + "]");
        });
        return rs;
    }

    private static void writeFile(String inPath, List<String> rs) throws IOException {
        Files.write(Paths.get(inPath), rs);
    }

    private static List<String> getFile(String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

}

class Test {
    public static void main(String[] args) {
        DBConvert.to_Entity("D:\\temp\\test.sql", "D:\\tmp\\test1111111111111.sql");
    }
}