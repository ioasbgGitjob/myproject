package com.self.utils.excel.poi;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * @author szy
 * @version 1.0
 * @description 列的格设置
 * SXSSF：是在XSSF基础上，POI3.8版本开始提供的一种支持低内存占用的操作方式，扩展名为.xlsx。
 * @date 2021-12-23 16:35:00
 */

public class CellStyleUtil_SXSSF {

    /**
     * 设置列宽
     */
    public void setColumnWidth() {

        String[] head = {"第一列","第二二列","第三三三三三三列"};

        SXSSFWorkbook work = new SXSSFWorkbook();
        SXSSFSheet sheet = work.createSheet("sheet1");

        //自动调整列宽 方案1
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < head.length; i++) {
            sheet.autoSizeColumn(i);
            //手动调整列宽，解决中文不能自适应问题
            //单元格单行最长支持255*256宽度（每个单元格样式已经设置自动换行，超出即换行）
            sheet.setColumnWidth(i, Math.min(255*256, sheet.getColumnWidth(i) * 12 / 10));
        }

        //自动调整列宽 方案2
        sheet.trackAllColumnsForAutoSizing();
        for (int i = 0; i < head.length; i++) {
            sheet.autoSizeColumn(i);
            //手动调整列宽，解决中文不能自适应问题
            //单元格单行最长支持255*256宽度（每个单元格样式已经设置自动换行，超出即换行）
            //设置最低列宽度，列宽约六个中文字符
            int width = Math.max(15 * 256, Math.min(255 * 256, sheet.getColumnWidth(i) * 12 / 10));
            sheet.setColumnWidth(i, width);
        }

    }


}
