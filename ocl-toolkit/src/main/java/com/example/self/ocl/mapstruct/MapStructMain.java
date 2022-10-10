package com.example.self.ocl.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author szy
 * @version 1.0
 * @description dto转换实例
 * @date 2021-11-02 15:44:12
 */

public class MapStructMain {

    @Test
    public void shouldMapCarToDto() {
        //given
        CarDO car = new CarDO("Morris", 5, CarType.SPORTS);

        //when
        CarDTO carDto = CarMapper.INSTANCE.carToCarDto(car);

        //then
        Assert.assertNotNull(carDto);
        Assert.assertEquals("Morris", carDto.getMake());
        Assert.assertEquals(5, carDto.getSeatCount());
        Assert.assertEquals("SPORTS", carDto.getType());
        System.out.println(carDto);
    }


}

/**
 * @author szy
 * @version 1.0
 * @description 源对象
 * @date 2021-11-02 15:46:48
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
class CarDO {
    private String make;
    private int numberOfSeats;
    private CarType type;
}

/**
 * @author szy
 * @version 1.0
 * @description 目标对象
 * @date 2021-11-02 15:47:24
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
class CarDTO {
    private String make;
    private String make2;
    private int seatCount;
    private String type;
    // 对象嵌套
    private CarDO myCarDO;
}

/**
 * @author szy
 * @version 1.0
 * @description 测试枚举
 * @date 2021-11-02 15:47:14
 */
enum CarType {
    /**
     * 普通
     */
    COMMON,
    /**
     * 老爷车
     */
    OLD,
    /**
     * 跑车
     */
    SPORTS;
}