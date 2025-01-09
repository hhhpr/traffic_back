package com.traffic.Controller;

import com.traffic.Mapper.CarMapper;
import com.traffic.Service.CarStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
//每25s自动执行一次
// 司机行为状态表功能，基于概率自动生成司机行为状态(控制车辆的state，0表示可接单，1表示拒绝接单，2表示车辆保养，无法接单)
public class CarStateTask {
    @Autowired
    private CarStateService carStateService;

    @Scheduled(cron = "0/25 * * * * ?" )
    public void changeCarState(){
        carStateService.changeCarService();
    }
}
