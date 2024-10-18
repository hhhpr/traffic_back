package com.traffic.Mapper;

import com.traffic.pojo.Car;
import com.traffic.pojo.Factory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InitMapper {

    void initFactoryTime(@Param("timestamps") List<String> timestamps,
                         @Param("ids") List<Integer> ids,
                         @Param("tableName")String tableName);

    Factory[] chooseFactory(@Param("factoryName") String factoryName);
    Car[] chooseCar();
}
