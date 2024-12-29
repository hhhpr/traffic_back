package com.traffic.Controller;

import com.traffic.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/15 * * * * ?" )
    public void orderTask(){
        //判断是否生成订单
        boolean ordercount = orderService.whetherProduct();

        if(!ordercount){
            orderService.orderProduct(); // 生成订单并修改库存信息
        } else {
            System.out.println("没有空闲车辆"); //返回前端没有空闲车辆的信息,这里只是输出信息
        }

    }

}
