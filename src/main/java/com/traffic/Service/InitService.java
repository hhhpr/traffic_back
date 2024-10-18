package com.traffic.Service;

import com.traffic.Mapper.InitMapper;
import com.traffic.pojo.Car;
import com.traffic.pojo.ReqBody;
import com.traffic.utils.GetColumnNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class InitService {

    @Autowired
    private InitMapper initMapper;
    @Autowired
    private GetColumnNum getColumnNum;
    public void initTime(String tableName,int low) {
        int columnNum=getColumnNum.getCount(tableName);
        // 使用 SimpleDateFormat 格式化时间戳
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> timestamps = new ArrayList<>();
        ArrayList<Integer> ids = new ArrayList<>();

        for (int i = 1; i <= columnNum; i++) {
            Random random=new Random();
            String ranNum= sdf.format(Timestamp.from(Instant.now().plusSeconds(random.nextInt(41)+low)));
            timestamps.add(ranNum);
            ids.add(i);
        }

        initMapper.initFactoryTime(timestamps,ids,tableName);
    }

    public void InitFactoryAndCar(ReqBody reqBody){
        List<Factory> factories=new ArrayList<Factory>();
        List<Car> cars=new ArrayList<Car>();


    }
}
