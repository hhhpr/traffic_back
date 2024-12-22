package com.traffic.Controller;

import com.traffic.Service.AutoProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AutoProduct {

    @Autowired
    private AutoProductService autoProductService;
    //工厂自动生产接口，每15s进行一次生产
    @Scheduled(cron = "0/15 * * * * ?" )
    public void productTask(){
        //一级工厂的生产，不需要原料，只考虑工厂库存积压
        autoProductService.AutoProduct("woodfactory1");

        //二级工厂的生产，考虑原料和现有库存
        autoProductService.AutoProduct("furniturefactory");
    }
}
