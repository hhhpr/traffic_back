package com.traffic.Controller;

import com.traffic.Service.InitService;
import com.traffic.pojo.ReqBody;
import com.traffic.pojo.Result;
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

    //初始化仓库时间信息和车辆空闲信息
    @GetMapping("/start")
    public Result initTime(@RequestParam("factoryName")String factoryName){
        initService.initTime("woodfactory1",5);
        initService.initTime("furniturefactory",15);
        initService.initTime("furniture",20);
        initService.initCar();
        return Result.success("初始化生产信息成功。");
    }

    //初始化后返回可进行仿真的仓库和车辆
    @PostMapping("/factoryAndCar")
    public Result initFactoryAndCar(@RequestBody ReqBody reqBody){
        List<Object> res = initService.backFactoryAndCar(reqBody);
        return Result.success(res);
    }
}
