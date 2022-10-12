package Excel工具.eec;

/**
 * 测试结论：SSD
 *  写入: 60W数据： eec：1.8秒  hutools+文件:17.8  hutools+stream:15.7
 *  写出excel: 20W数据 24MB, eec: 4秒 , hutools: 100秒
 * ecc-example： https://github.com/wangguanquan/eec-example.git
 * Created by szy on 2021/4/15
 */
public class ExcelMain {

    public static void main(String[] args) throws Exception {

        Thread.sleep(2000);
        Excel4EEC.test1();
        Thread.sleep(2000);
        Excel4Hutools.test1();
        Thread.sleep(2000);
        Excel4Hutools.test2();
        Thread.sleep(2000);

    }

}
