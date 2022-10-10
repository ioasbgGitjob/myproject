package 策略模式;

/**
 * @author szy
 * @version 1.0
 * @description 模拟计算器
 * @date 2022-06-15 11:29:24
 */
public interface ICalculation {

    /**
     * 计算
     */
    int cal(int a, int b);


    CalTypeEnum getType();
}

enum CalTypeEnum {
    ADD, SUBTRACT,MULTIPLY;
}
