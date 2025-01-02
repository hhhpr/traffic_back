package com.traffic.Service;

import com.traffic.Mapper.OrderMapper;
import com.traffic.pojo.Car;
import com.traffic.pojo.Factory;
import com.traffic.pojo.Order;
import com.traffic.pojo.TotalOrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    public void orderProduct(){
        int carcount = orderMapper.getCarCount();
        int ordercount = carcount - orderMapper.getOrderCount(0);
        for (int i = 0; i < ordercount; i++) {

            Order order = new Order();

            order.setGenerationtime(new Timestamp(System.currentTimeMillis())); //获取当前时间

            Factory[] startfactory = orderMapper.getOrderFactory(1); // 获取一级工厂
            Factory[] endfactory = orderMapper.getOrderFactory(2); //获取二级工厂
            Random random = new Random();
            int ocount = random.nextInt(1, endfactory[0].getTotalInventory() - endfactory[0].getGoodsInventory() - endfactory[0].getRawInventory()); //需要运输的货物量
            // 先生成订单需要的数量（随机），再和good存量比较，不够则原料转化，再不够则最多的数量为订单数
            if (ocount < startfactory[0].getGoodsInventory()){
                startfactory[0].setGoodsInventory(startfactory[0].getGoodsInventory() - ocount);
            } else {
                if (ocount < startfactory[0].getGoodsInventory() + startfactory[0].getRawInventory() * startfactory[0].getTransRate()){
                    startfactory[0].setRawInventory((int) (startfactory[0].getRawInventory() - ((ocount - startfactory[0].getGoodsInventory()) / startfactory[0].getTransRate())));
                    startfactory[0].setGoodsInventory(0);
                }
                else {
                    ocount = (int) (startfactory[0].getGoodsInventory() + startfactory[0].getRawInventory() * startfactory[0].getTransRate());
                    startfactory[0].setGoodsInventory(0);
                    startfactory[0].setRawInventory(0);
                }
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
            orderMapper.updateFactoryInventory(startfactory);
            System.out.println("已更新wood工厂信息");
            orderMapper.updateFactoryInventory(endfactory);
            System.out.println("已更新furniture工厂信息");
        }
    }

    public TotalOrderInfo[] getTotalOrderInfo() {
        TotalOrderInfo[] totalOrders=orderMapper.fetchOrder();
        if(totalOrders.length!=0){
            orderMapper.updateOrderState(totalOrders,1);
        }
        return totalOrders;
    }
}
