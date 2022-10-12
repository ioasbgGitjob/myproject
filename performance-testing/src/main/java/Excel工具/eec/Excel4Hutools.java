package Excel工具.eec;


import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.nio.file.Paths;
import java.util.List;

/**
 * Excel 性能测试
 * 工具： Hutools
 * Created by szy on 2021/4/15
 */
public class Excel4Hutools {

    public static void test1() throws Exception{
        String memo = "hutools从文件中读取excel";
        Long l1 = System.currentTimeMillis();
        // TODO
        ExcelReader reader = ExcelUtil.getReader("D:\\temp\\收入-微粒贷202103.xlsx");
        List<List<Object>> aa  = reader.read();
        System.out.println(aa.size());
        System.out.println("-----------"+memo+"用时"+(System.currentTimeMillis()-l1));
    }
    public static void test2() throws Exception{
        String memo = "hutools从流中读取excel";
        Long l1 = System.currentTimeMillis();
        // TODO
        ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("D:\\temp\\收入-微粒贷202103.xlsx"));
        List<List<Object>> aa = reader.read();
        System.out.println(aa.size());
        System.out.println("-----------"+memo+"用时"+(System.currentTimeMillis()-l1));
    }

}
