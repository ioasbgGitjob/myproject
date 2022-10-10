package com.self.utils.excel.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.junit.Test;

import java.io.FileOutputStream;

/**
 * @author szy
 * @version 1.0
 * @description 列的格设置
 * @description HSSF：操作Excel97-2003版本，扩展名为.xls。
 * @date 2021-12-23 16:35:00
 */

public class CellStyleUtil_HSSF {

    @Test
    public void Demo() {
        // 创建新的Excel 工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();

        // 在Excel工作簿中建一工作表，其名为缺省值, 也可以指定Sheet名称
        HSSFSheet sheet = workbook.createSheet();
        //HSSFSheet sheet = workbook.createSheet("SheetName");

        // 用于格式化单元格的数据
        HSSFDataFormat format = workbook.createDataFormat();

        // 创建新行(row),并将单元格(cell)放入其中. 行号从0开始计算.
        HSSFRow row = sheet.createRow((short) 1);

        // 设置字体
        HSSFFont font = workbook.createFont();
        font.setFontHeightInPoints((short) 20); //字体高度
        font.setColor(HSSFFont.COLOR_RED); //字体颜色
        font.setFontName("黑体"); //字体
//        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //宽度
        font.setItalic(true); //是否使用斜体
//        font.setStrikeout(true); //是否使用划线

        // 设置单元格类型
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setAlignment(HorizontalAlignment.CENTER); //水平布局：居中
        cellStyle.setWrapText(true);

        // 添加单元格注释
        // 创建HSSFPatriarch对象,HSSFPatriarch是所有注释的容器.
        HSSFPatriarch patr = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patr.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者. 当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("Xuys.");

        // 创建单元格
        HSSFCell cell = row.createCell((short) 1);
        HSSFRichTextString hssfString = new HSSFRichTextString("王德法大大大大");
        cell.setCellValue(hssfString);//设置单元格内容
        cell.setCellStyle(cellStyle);//设置单元格样式
        cell.setCellType(CellType.STRING);//指定单元格格式：数值、公式或字符串
        cell.setCellComment(comment);//添加注释

        //格式化数据
        row = sheet.createRow((short) 2);
        cell = row.createCell((short) 2);
        cell.setCellValue(11111.25);
        cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format.getFormat("0.0"));
        cell.setCellStyle(cellStyle);

        row = sheet.createRow((short) 3);
        cell = row.createCell((short) 3);
        cell.setCellValue(9736279.073);
        cellStyle = workbook.createCellStyle();
        cellStyle.setDataFormat(format.getFormat("#,##0.0000"));
        cell.setCellStyle(cellStyle);


        sheet.autoSizeColumn((short) 0); //调整第一列宽度
        sheet.autoSizeColumn((short) 1); //调整第二列宽度
        sheet.autoSizeColumn((short) 2); //调整第三列宽度
        sheet.autoSizeColumn((short) 3); //调整第四列宽度

        try {
            FileOutputStream fileOut = new FileOutputStream("D:\\tmp\\PoiStyle.xls");
            workbook.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


}
