package com.traffic.Mapper;

import com.traffic.pojo.Marker;
import org.apache.ibatis.annotations.*;

import java.util.Map;

@Mapper
public interface FetchData {

//    @Insert("insert into")
//    void storePOIs(List<Map<String, Object>> pois) {
//        String sql = "INSERT INTO poi (id, name, address, location) VALUES (?, ?, ?, ?)";
//        PreparedStatement statement = connection.prepareStatement(sql);
//
//        for (Map<String, Object> poi : pois) {
//            statement.setString(1, (String) poi.get("id"));
//            statement.setString(2, (String) poi.get("name"));
//            statement.setString(3, (String) poi.get("address"));
//            statement.setString(4, (String) poi.get("location"));
//            statement.addBatch();
//        }
//
//        statement.executeBatch();
//        statement.close();
//        connection.close();
//    }
    public void storePOIs(Map<String, String> poi);

    Marker[] woodFacPOIList();
    Marker[] furFacPOIList();
    Marker[] furPOIList();

}
