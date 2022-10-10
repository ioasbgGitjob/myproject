package 策略模式;

import java.util.List;

/**
 * @author szy
 * @version 1.0
 * @description 策略模式 调用类
 * @date 2022-06-15 11:35:55
 */

public class StrategyMain {

    public static void main(String[] args) {
        // 策略模式的 直接调用
        // AddCal() 实现了加法运算
        CalManagerSimple calManager = new CalManagerSimple(new AddCal());
        System.out.println(calManager.doCalculate(1, 3));

        // 策略模式+工厂方法 调用
        CalManagerFactory calManagerFactory = new CalManagerFactory();
        System.out.println(calManagerFactory.doCalculate(CalTypeEnum.ADD, 10, 20));
        // 添加工厂方法
        calManagerFactory.setMaps(List.of(new MultiplyCal()));
        System.out.println(calManagerFactory.doCalculate(CalTypeEnum.MULTIPLY, 10, 20));

    }

}


