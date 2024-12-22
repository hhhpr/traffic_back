package com.traffic.Mapper;

import com.traffic.pojo.Factory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AutoProductMapper {
    //查询可以进行生产的工厂，按升序排列
    Factory[] enableToProductFactory(@Param("tableName") String tableName);

    void updateInventory(@Param("factories") Factory[] factories,@Param("tableName") String tableName);
}
