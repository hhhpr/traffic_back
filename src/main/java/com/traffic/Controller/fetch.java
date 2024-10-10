package com.traffic.Controller;

import com.traffic.Service.Factory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class fetch {

    @Autowired
    private Factory factory;

    @RequestMapping(value = "/depts",method = RequestMethod.GET)
    public String fetchFacList(){
        factory.fetchfacdata();
        return "OK";
    }
}
