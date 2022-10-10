package 策略模式;

/**
 * @author szy
 * @version 1.0
 * @description 实现计算的 具体策略
 * @date 2022-06-15 12:09:35
 */

public class AllCal {
// 为了方便观察  放到一个类了,  实际开发中不建议这样搞
}

class AddCal implements ICalculation {
    @Override
    public int cal(int a, int b) {
        return a + b;
    }

    @Override
    public CalTypeEnum getType() {
        return CalTypeEnum.ADD;
    }
}

class SubTractCal implements ICalculation {
    @Override
    public int cal(int a, int b) {
        return a - b;
    }

    @Override
    public CalTypeEnum getType() {
        return CalTypeEnum.SUBTRACT;
    }
}

class MultiplyCal implements ICalculation {

    @Override
    public int cal(int a, int b) {
        return a*b;
    }

    @Override
    public CalTypeEnum getType() {
        return CalTypeEnum.MULTIPLY;
    }
}

