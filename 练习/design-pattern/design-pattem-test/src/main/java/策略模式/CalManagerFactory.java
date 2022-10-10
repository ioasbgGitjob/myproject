package 策略模式;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author szy
 * @version 1.0
 * @description 策略模式  + 简单工厂实现计算
 * @date 2022-06-15 11:35:55
 */

public class CalManagerFactory {

    // 创建ICalculation工厂
    private Map<CalTypeEnum, ICalculation> maps = new HashMap();

    // 默认初始化所有策略
    CalManagerFactory() {
        maps.put(CalTypeEnum.ADD, new AddCal());
        maps.put(CalTypeEnum.SUBTRACT, new SubTractCal());
    }

    public int doCalculate(CalTypeEnum typeEnum, int a, int b) {
        return maps.get(typeEnum).cal(a, b);
    }

    /**
     * 添加工厂方法
     * @param list
     */
    public void setMaps(List<ICalculation> list) {
        if (list == null || list.size() <= 0) {
            System.out.println("failed");
        }
        list.stream().forEach(P -> maps.put(P.getType(), P));
    }


}

