package com.traffic.Controller;

import com.traffic.Service.UpdateService;
import com.traffic.pojo.Result;
import com.traffic.pojo.UpdateBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/update")
public class UpdateStatus {

    @Autowired
    private UpdateService updateService;
    @PostMapping("/timeAndIsready")
    public Result update(@RequestBody UpdateBody updateBody){
        updateService.updateTimeAndIsready(updateBody);
        return Result.success("更新状态成功");
    }
}
