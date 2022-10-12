package 复制对象;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.StopWatch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @名字 复制对象
 * @应用场景说明 两个bean互相转换, 比如dto转vo
 * @任务
 * @结论 使用set,和构造函数都差不多,  但是千万别用 BeanUtil.copyProperties
 */
public class CvtBean {

    @Test
    public void mainTest() {
        List<E1> e1List = new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            e1List.add(new E1("张" + i, "上海市", i, i * 3l, LocalDateTime.now()));
        }

        for (int i = 0; i < 4; i++) {

            StopWatch s = new StopWatch();
            test1(s, e1List);
            test2(s, e1List);
            test3(s, e1List);
            System.out.println(s.getTotalTimeSeconds());
            System.out.println(s.prettyPrint());

        }

    }

    public void test1(StopWatch s, List<E1> e1s) {
        s.start("通过set");
        // TODO
        List<E2> e2List = new ArrayList<>();
        for (E1 e1 : e1s) {
            E2 e2 = new E2();
            e2.setAddress(e1.getAddress());
            e2.setBirthday(e1.getBirthday());
            e2List.add(e2);
        }
        s.stop();
    }

    public void test2(StopWatch s, List<E1> e1s) {
        s.start("通过构造函数");
        // TODO
        List<E2> e2List = new ArrayList<>();
        for (E1 e1 : e1s) {
            e2List.add(new E2(e1.getAddress(), e1.getBirthday()));
        }
        s.stop();
    }

    public void test3(StopWatch s, List<E1> e1s) {
        s.start("通过BeanUtil.copyProperties");
        // TODO
        List<E2> e2List = new ArrayList<>();
        for (E1 e1 : e1s) {
            E2 e2 = new E2();
            BeanUtil.copyProperties(e1, e2);
            e2List.add(e2);
        }
        s.stop();
    }


    @AllArgsConstructor
    @Data
    class E1 {
        private String name;
        private String address;
        private Integer age;
        private Long amount;
        private LocalDateTime birthday;
    }

    @Data
    @NoArgsConstructor
    class E2 {
        private String name;
        private String address;
        private Integer age;
        private Long amount;
        private LocalDateTime birthday;

        public E2(String address, LocalDateTime birthday) {
            this.address = address;
            this.birthday = birthday;
        }
    }


}
