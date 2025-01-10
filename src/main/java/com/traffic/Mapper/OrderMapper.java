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

    Factory[] getStartOrderFactory(@Param("clas") int clas);

    Factory[] getEndOrderFactory(@Param("clas") int clas);


    void updateFactoryInventory(@Param("factories") Factory[] factories);

    int createOrder(Order order);

    Car[] chooseCars();

    TotalOrderInfo[] fetchOrder();

    //用于修改订单状态
    void updateOrderState(TotalOrderInfo[] totalOrders,int state);

    void updateCarState(@Param("isready") int isready, @Param("cid") int cid);

    Order[] getIdOrder(@Param("id") int id);

    Factory[] getIdFactory(@Param("id") int id);

    void updateOrderStateTo(@Param("state") int state, @Param("id") int id);

    int getCarState(@Param("carId") int carId);

    void setFacGoodInventory(@Param("id") int id,@Param("goodsInventory") int goodsInventory);
    void setFacRawInventory(@Param("id") int id,@Param("rawInventory") int rawInventory);

}
