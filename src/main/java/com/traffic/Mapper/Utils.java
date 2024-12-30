package com.traffic.Mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface Utils {
    int getRowCount(String tableName);
}
