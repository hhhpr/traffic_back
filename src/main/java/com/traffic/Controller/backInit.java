package com.traffic.Controller;

import com.traffic.Service.InitService;
import com.traffic.Service.OrderService;
import com.traffic.pojo.ReqBody;
import com.traffic.pojo.Result;
import com.traffic.pojo.TotalOrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/init")
public class backInit {

    @Autowired
    private InitService initService;

    @Autowired
    private OrderService orderService;

    //初始化仓库时间信息和车辆空闲信息，随机生成一个时间，到达该时间点后表示该工厂有货物需要运输。将所有车辆状态置为空闲状态
    @GetMapping("/start")
    public Result initTime(@RequestParam("factoryName")String factoryName){
        initService.initTime("woodfactory1",5);
        initService.initTime("furniturefactory",15);
        initService.initTime("furniture",20);
        initService.initCar();
        return Result.success("初始化生产信息成功。");
    }

    //返回可进行运货的工厂和车辆，用于通知车辆到该工厂位置取货
    @PostMapping("/factoryAndCar")
    public Result initFactoryAndCar(@RequestBody ReqBody reqBody){
        List<Object> res = initService.backFactoryAndCar(reqBody);
        return Result.success(res);
    }

    //返回需要进行运输的订单
    @RequestMapping(value = "/fetchOrder",method =RequestMethod.GET)
    public TotalOrderInfo[] fetchOrderInfo(){
        return orderService.getTotalOrderInfo();
    }
}
