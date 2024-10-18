package com.traffic.Controller;

import com.traffic.Service.InitService;
import com.traffic.pojo.ReqBody;
import com.traffic.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/init")
public class backInit {

    @Autowired
    private InitService initService;

    @GetMapping("/start")
    public Result initTime(@RequestParam("factoryName")String factoryName){
        initService.initTime("woodfactory1",15);
        initService.initTime("furniturefactory",30);
        initService.initTime("furniture",45);
        return Result.success("初始化生产信息成功。");
    }

    @PostMapping("/factoryAndCar")
    public Result initFactoryAndCar(@RequestBody ReqBody reqBody){
        initService.InitFactoryAndCar(reqBody);
        return Result.success("OK");
    }
}
