package com.traffic.utils;

import com.traffic.Mapper.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GetColumnNum {
    @Autowired
    private Utils utils;
    public int getCount(String tableName){
        return utils.getRowCount(tableName);
    }
}
