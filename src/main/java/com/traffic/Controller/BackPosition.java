package com.traffic.Controller;

import com.traffic.Service.PositionService;
import com.traffic.pojo.Position;
import com.traffic.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BackPosition {

    @Autowired
    private PositionService positionService;

    @GetMapping("/PositionList")
    public Result backPositionData(){
        Position[] position=positionService.getPosition();
        return Result.success(position);
    }


}
