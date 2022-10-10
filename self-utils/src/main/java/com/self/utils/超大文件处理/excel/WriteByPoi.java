package com.self.utils.超大文件处理.excel;

import cn.hutool.core.date.StopWatch;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * @author szy
 * @version 1.0
 * @description 写大文件
 * 使用Poi中的 SXSSFWorkbook
 * 100W数据, 82.2秒, 内存占用比较少(普通的XSSFWorkbook直接oom)
 * @date 2022-01-19 11:41:45
 */

public class WriteByPoi {

    private int totalRowNumber = 100000; //写入的excel数据行数
    private int totalCellNumber = 40; //excel每行共40列

    @Test
    public void t() throws Exception {
        StopWatch s = new StopWatch();
        s.start();
        // TODO:
        writeBigExcelTest();
        s.stop();
        System.out.println("------------------");
        System.out.println(s.getTotalTimeSeconds());

//        Thread.sleep(100000);
    }

    @Test
    public void writeBigExcelTest() {
        try (SXSSFWorkbook wb = new SXSSFWorkbook();//默认100行，超100行将写入临时文件;
             //Write excel to a file
             FileOutputStream out = new FileOutputStream("D:\\tmp\\hugeExcel_" + totalRowNumber + ".xlsx");
//             SXSSFWorkbook wb = file.exists() ? new SXSSFWorkbook(new XSSFWorkbook(new FileInputStream(file))) : new SXSSFWorkbook(); // 追加到之前的文件后边
        ) {
            wb.setCompressTempFiles(false); //是否压缩临时文件，否则写入速度更快，但更占磁盘，但程序最后是会将临时文件删掉的
            Sheet sheet = wb.createSheet("Sheet 1");
            //定义Row和Cell变量, Rows从0开始.
            Row row;
            Cell cell;
            for (int rowNumber = 0; rowNumber < totalRowNumber; rowNumber++) {
                row = sheet.createRow(rowNumber);
                for (int cellNumber = 0; cellNumber < totalCellNumber; cellNumber++) {
                    cell = row.createCell(cellNumber);
                    cell.setCellValue(Math.random()); //写入一个随机数
                }
                //打印测试，
                if (rowNumber % 10000 == 0) {
                    System.out.println(rowNumber);
                }
            }
            wb.write(out);
            wb.dispose();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
