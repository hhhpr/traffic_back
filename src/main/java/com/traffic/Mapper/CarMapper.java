package com.traffic.Mapper;

import com.traffic.pojo.Car;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CarMapper {
    Car[] getCar();

    void setCarState(@Param("carId") int carId, @Param("state") int state);
}
