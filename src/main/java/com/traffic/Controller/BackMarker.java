package com.traffic.Controller;

import com.traffic.Service.MarkerService;
import com.traffic.pojo.Marker;
import com.traffic.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class BackMarker {

    @Autowired
    private MarkerService markerService;

    @GetMapping("/woodFacMarkerList")
    public Result backwoodFacMarkerData1(){
        Marker[] data= markerService.getwoodFacMarker();
        return Result.success(data);
    }
    @GetMapping("/furFacMarkerList")
    public Result backfurFacMarkerData(){
        Marker[] data= markerService.getfurFacMarker();
        return Result.success(data);
    }
    @GetMapping("/furMarkerList")
    public Result backfurMarkerData(){
        Marker[] data= markerService.getfurMarker();
        return Result.success(data);
    }
}
