package 策略模式;

import java.util.HashMap;
import java.util.Map;

/**
 * @author szy
 * @version 1.0
 * @description 策略模式  管理策略的类
 * @date 2022-06-15 11:35:55
 */

public class CalManagerSimple {

    // 简单策略模式得实现
    private ICalculation ICalculation;

    public CalManagerSimple(ICalculation iCalculation) {
        ICalculation = iCalculation;
    }

    public int doCalculate(int a, int b) {
        return ICalculation.cal(a, b);
    }

}

