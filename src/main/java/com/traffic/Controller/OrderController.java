package com.traffic.Controller;

import com.traffic.Service.OrderService;
import com.traffic.pojo.Result;
import com.traffic.pojo.UpdateOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
            int res = orderService.orderProduct(); // 生成订单并修改库存信息
            if(res == 0){
                System.out.println("生成了新订单");
            } else if (res == 1) {
                System.out.println("没有空闲车辆"); //返回前端没有空闲车辆的信息,这里只是输出信息
            } else if (res == 2) {
                System.out.println("当前不存在没有订单的工厂");
            }
        }
    }

    @PostMapping("/updateOrder")
    public Result updateOrderState(@RequestBody UpdateOrder updateOrder){
        int clas = orderService.updateOrderTo(updateOrder);
        if(clas == 0){
            return Result.success("订单更新取货状态成功");
        } else if(clas == 1){
            return Result.success("订单更新送货状态成功");
        } else {
            return Result.error("更新订单状态失败");
        }
    }

}
