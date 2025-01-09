package com.traffic.Controller;

import com.traffic.Mapper.CarMapper;
import com.traffic.Service.CarStateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController

public class CarStateTask {
    @Autowired
    private CarStateService carStateService;

    @Scheduled(cron = "0/25 * * * * ?" )
    public void changeCarState(){
        carStateService.changeCarService();
    }
}
