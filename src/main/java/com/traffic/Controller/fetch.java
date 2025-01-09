package com.traffic.Controller;

import com.traffic.Service.Factory;
import com.traffic.Service.OrderService;
import com.traffic.pojo.TotalOrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


//之前用于向数据库批量添加工厂，现在暂时无用，不用管
@Slf4j
@RestController
public class fetch {

    @Autowired
    private Factory factory;

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    public String fetchFacList(){
        factory.fetchfacdata();
        return "OK";
    }
}
