package com.traffic.Mapper;

import com.traffic.pojo.Car;
import com.traffic.pojo.Factory;
import com.traffic.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    Integer getOrderCount(@Param("state") int state);

    Integer getCarCount();

    Factory[] getOrderFactory(@Param("amount") int amount, @Param("tableName") String tableName);

    void updateFactoryInventory(@Param("factories") Factory[] factories, @Param("tableName") String tableName);

    int createOrder(Order order);

    Car[] chooseCars(@Param("amount") int amount);

}
