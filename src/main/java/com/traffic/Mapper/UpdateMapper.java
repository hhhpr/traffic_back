package com.traffic.Mapper;

import com.traffic.pojo.UpdateBody;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UpdateMapper {
    void updateTime(@Param("factoryId") int factoryId,
                    @Param("ranNum") String ranNum,
                    @Param("factoryName") String factoryName);
    void updateCar(@Param("carId") int carId,
                   @Param("longitude") double longitude,
                   @Param("latitude") double latitude,
                   @Param("isready") int isready);

    UpdateBody[] getFactoryGoods(@Param("factoryName") String factoryName,
                               @Param("id") int id);
}
