package com.traffic.Controller;

import com.traffic.Service.UpdateService;
import com.traffic.pojo.Result;
import com.traffic.pojo.UpdateBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//弃用，不用管
@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateStatus {

    @Autowired
    private UpdateService updateService;

    //更新工厂可仿真时间，用于车辆取货后更新该工厂下一次可取货的时间
    @PostMapping("/time")
    public Result updateT(@RequestBody UpdateBody updateBody){
        updateService.updateTime(updateBody);
        return Result.success("更新工厂生产信息成功");
    }

    //更新车辆状态，用于车辆取货并完成仿真后修改状态和位置
    @PostMapping("/isReady")
    public Result updateI(@RequestBody UpdateBody updateBody){
        updateService.updateIsReady(updateBody);
        return Result.success("更新车辆状态成功");
    }

    //取货，用于车辆到达可取货工厂后取货，随机返回一个下一级工厂信息
    @PostMapping("/getGoods")
    public Result getGoods(@RequestBody UpdateBody updateBody){
        UpdateBody retUpdateBody= new UpdateBody();
        retUpdateBody= updateService.getGoods(updateBody);
        return Result.success(retUpdateBody);
    }
}
