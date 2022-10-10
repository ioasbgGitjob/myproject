package com.ocl.toolkit.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author szy
 * @version 1.0
 * @description
 * @date 2021-11-02 15:45:37
 */
@Mapper
public interface CarMapper {
    CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

    @Mapping(source = "numberOfSeats", target = "seatCount")
    @Mapping(source = "make", target = "myCarDO.make")
    CarDTO carToCarDto(CarDO car);
}
