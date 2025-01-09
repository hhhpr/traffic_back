package com.traffic.Service;

import com.traffic.Mapper.FetchData;
import com.traffic.pojo.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarkerService {

    @Autowired
    private FetchData fetchData;
    //弃用
//    public Marker[] getwoodFacMarker() {
//        return fetchData.woodFacPOIList();
//    }
//    public Marker[] getfurFacMarker() {
//        return fetchData.furFacPOIList();
//    }
//    public Marker[] getfurMarker() {
//        return fetchData.furPOIList();
//    }

    //返回所用工厂点位供前端初始化地图
    public Marker[] getFacMarker() {
        return fetchData.FacPOIList();
    }
}
