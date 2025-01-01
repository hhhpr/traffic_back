package com.traffic.Mapper;

import com.traffic.pojo.Car;
import com.traffic.pojo.Factory;
import com.traffic.pojo.Order;
import com.traffic.pojo.TotalOrderInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {
    int getOrderCount(@Param("state") int state);

    int getCarCount();

    Factory[] getOrderFactory(@Param("clas") int clas);

    void updateFactoryInventory(@Param("factories") Factory[] factories);

    int createOrder(Order order);

    Car[] chooseCars();

    TotalOrderInfo[] fetchOrder();

}
