package Excel工具.eec;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.ttzero.excel.annotation.ExcelColumn;
import org.ttzero.excel.annotation.HeaderStyle;
import org.ttzero.excel.entity.ListSheet;
import org.ttzero.excel.entity.Sheet;
import org.ttzero.excel.entity.Workbook;
import org.ttzero.excel.processor.IntConversionProcessor;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * ECC导入导出 示例
 */

public class EecDemo {

    /**
     * 导出学生信息
     */
    @Test
    public void exportStudent() throws IOException {
        List<Student> students = new ArrayList<>();
        Student s = new Student(1, "1", new BigDecimal("1"));
        students.add(s);


        new Workbook("二年级学生表")// 新增一个Workbook并指定名称，也就是Excel文件名
//                .cancelOddFill()// 去除斑马纹
                .addSheet(new ListSheet<>(students)) // 添加一个Sheet页，并指定导出数据
                .writeTo(Paths.get("D:\\data")); // 指定导出位置
    }


    public void exportExcel() {

//        // 定义一个动态改变填充的lambda表达式，成绩低于60分标黄
//        StyleProcessor sp = (o, style, sst) -> {
//            if ((int)o < 60) {
//                style = Styles.clearFill(style)
//                        | sst.addFill(new Fill(PatternType.solid, Color.orange));
//            }
//            return style;
//        };

        try {
            IntConversionProcessor conversion = n -> n < 60 ? "不及格" : n;
            List<EccEntity> e = new ArrayList<>();
            EccEntity e1 = new EccEntity("1", 10L, new EccEntity1("2", 22l));
            for (int i = 0; i < 10; i++) {
                e.add(e1);
            }
            new Workbook("哇卡卡卡").addSheet(
                            "sheet11"
                            , e
                            , new Sheet.Column("aa", "id", 2)
                            , new Sheet.Column("eccEntity1", "eccEntity1", EccEntity1.class)
//                                    .setStyleProcessor(sp)
                    )
                    .addSheet(new ListSheet<>("学生信息", e))
                    .writeTo(Paths.get("D:/data"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EccEntity {
    @ExcelColumn("渠道ID")
    private String id;
    @ExcelColumn("金额")
    private Long amt;
    @ExcelColumn("哈哈哈")
    private EccEntity1 eccEntity1;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class EccEntity1 {
    @ExcelColumn("渠道ID")
    private String id;
    @ExcelColumn("金额")

    private Long amt;
}

@Data
@AllArgsConstructor
@NoArgsConstructor
class Student {
    @ExcelColumn("啊啊")
    @HeaderStyle(fillFgColor = "", fontColor = "red") // 表头格式
    private int id;
    private String name;
    private BigDecimal amt;
}