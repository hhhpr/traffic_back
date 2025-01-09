package com.traffic.Service;

import com.traffic.Mapper.OrderMapper;
import com.traffic.pojo.*;
import com.traffic.pojo.Factory;
import org.apache.ibatis.jdbc.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderMapper orderMapper;

    public boolean whetherProduct(){
        //是否生产货物，比较正在运输的订单状态与车辆数
        return orderMapper.getOrderCount(1) == orderMapper.getCarCount();
    }

    public int orderProduct(){
        int carcount = orderMapper.getCarCount();
        int ordercount = carcount - orderMapper.getOrderCount(0);
        if (ordercount <= 0) return 1;
        for (int i = 0; i < ordercount; i++) {

            Order order = new Order();
            Random random = new Random();

            order.setGeneration_time(new Timestamp(System.currentTimeMillis())); //获取当前时间

            int clas = random.nextInt(2) + 1;
            System.out.println(clas);
            Factory[] startfactory = orderMapper.getStartOrderFactory(clas); // 获取上级工厂
            Factory[] endfactory = orderMapper.getEndOrderFactory(clas + 1); //获取下级工厂
            System.out.println("-----------------------------------------------");
            System.out.println("startfactory:" + startfactory[0].getName());
            System.out.println("endfactory:" + endfactory[0].getName());
            System.out.println("-----------------------------------------------");

            if(startfactory.length == 0 || endfactory.length == 0) return 2;

            int ocount = random.nextInt(1, endfactory[0].getTotalInventory() - endfactory[0].getGoodsInventory() - endfactory[0].getRawInventory()); //需要运输的货物量
            // 先生成订单需要的数量（下级工厂还能容纳的库存），再和good存量比较，不够则最多的数量为订单数
            if (ocount < startfactory[0].getGoodsInventory()){
                startfactory[0].setGoodsInventory(startfactory[0].getGoodsInventory() - ocount);
            } else {
                ocount = startfactory[0].getGoodsInventory();
                startfactory[0].setGoodsInventory(0);
            }
            endfactory[0].setRawInventory(endfactory[0].getRawInventory() + ocount);

            //车辆匹配
            int carid = 0;
            double dis = 0;
            Car[] cars = orderMapper.chooseCars();
            for (Car car : cars){
                if(car != null){

                    //使用勾股定理计算两点之间的距离
                    double xdisSquare = (car.getLatitude() - startfactory[0].getLatitude()) * (car.getLatitude() - startfactory[0].getLatitude());
                    double ydisSquare = (car.getLongitude() - startfactory[0].getLongitude()) * (car.getLongitude() - startfactory[0].getLongitude());
                    double cdis = Math.sqrt(xdisSquare + ydisSquare);

                    //记录最近的匹配的车
                    if(cdis > dis){
                        dis = cdis;
                        carid = car.getId();
                    }
                }
            }
            //更新车辆状态
            orderMapper.updateCarState(0,carid);

            //生成订单的各项信息
            order.setStartfactoryid(startfactory[0].getId());
            order.setStartfactoryname(startfactory[0].getName());
            order.setStartlatitude(startfactory[0].getLatitude());
            order.setStartlongitude(startfactory[0].getLongitude());
            order.setEndfactoryid(endfactory[0].getId());
            order.setEndfactoryname(endfactory[0].getName());
            order.setEndlatitude(endfactory[0].getLatitude());
            order.setEndlongitude(endfactory[0].getLongitude());
            order.setGoodid(startfactory[0].getGoodsID());
            order.setGoodcount(ocount);
            order.setCarid(carid);

            //创建订单
            orderMapper.createOrder(order);
            System.out.println("已创建一条新订单");


            //更新工厂信息
//            orderMapper.updateFactoryInventory(startfactory);
//            System.out.println("已更新wood工厂信息");
//            orderMapper.updateFactoryInventory(endfactory);
//            System.out.println("已更新furniture工厂信息");
        }
        return 0;
    }

    public TotalOrderInfo[] getTotalOrderInfo() {
        TotalOrderInfo[] totalOrders=orderMapper.fetchOrder();
        if(totalOrders.length!=0){
            orderMapper.updateOrderState(totalOrders,1);
        }
        return totalOrders;
    }

    public int updateOrderTo(UpdateOrder updateOrder){
        Order[] orders = orderMapper.getIdOrder(updateOrder.getOrderId());
        if (updateOrder.getState() == 1){
            //更新工厂信息
            Factory[] factories = orderMapper.getIdFactory(orders[0].getStartfactoryid());
            factories[0].setGoodsInventory(factories[0].getGoodsInventory() - orders[0].getGoodcount());
            return 0;
        }else {
            //更新工厂信息
            Factory[] factories = orderMapper.getIdFactory(orders[0].getEndfactoryid());
            factories[0].setRawInventory(factories[0].getRawInventory() + orders[0].getGoodcount());
            //更新车辆信息为待命
            orderMapper.updateCarState(1, orders[0].getCarid());
            //更新订单信息为完成
            orderMapper.updateOrderStateTo(2, orders[0].getId());
            return 1;
        }
    }

    public int carState(int carId){
        return orderMapper.getCarState(carId);
    }

}
