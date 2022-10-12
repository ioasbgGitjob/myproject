package Excel工具.eec;

import org.ttzero.excel.entity.ExcelWriteException;
import org.ttzero.excel.entity.ListSheet;
import org.ttzero.excel.entity.Workbook;
import org.ttzero.excel.reader.ExcelReader;
import org.ttzero.excel.reader.Row;
import org.ttzero.excel.reader.Sheet;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Excel 性能测试
 * 工具： EEC（Excel Export Core）
 * 参考文件地址： https://blog.csdn.net/huanghui7635/article/details/86525628
 * git地址：https://github.com/wangguanquan/eec
 * Created by szy on 2021/4/15
 */
public class Excel4EEC {


    public static void test1() throws Exception{
        String memo = "eec读取excel";
        Long l1 = System.currentTimeMillis();
        // TODO
//        List<Row> aa = ExcelReader.read(Paths.get("D:\\temp\\收入-微粒贷202103.xlsx")).sheets().flatMap(Sheet::rows).collect(Collectors.toList());
        ExcelReader.read(Paths.get("D:\\data\\二年级学生表 - 副本.xls"))
                .sheets().flatMap(Sheet::rows).forEach(e ->{
            System.out.println(e);
        });


        ExcelReader.read(Paths.get("D:\\temp\\收入-微粒贷202103 - 副本.xlsx"))
                .sheets().flatMap(Sheet::rows).forEach(e ->{
            System.out.println(e);
        });

        System.out.println("-----------"+memo+"用时"+(System.currentTimeMillis()-l1));
    }

    /**
     * 写出 excel文件
     * 参考文件 https://github.com/wangguanquan/eec/wiki/1-%E5%86%99%E5%8D%95Worksheet
     */
    public static void writeFile(){
        // 导出到对象
//        new Workbook().addSheet(new ListSheet<>(students)).writeTo(Paths.get("e:/excel"));
        // 导出到outputStream
//        new Workbook().addSheet(new ListSheet<>(students)).writeTo(response.getOutputStream());
    }

    public static void main(String[] args) throws Exception {
        test1();
    }

}

