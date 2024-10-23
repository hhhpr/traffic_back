package com.traffic.Service;

import com.traffic.Mapper.InitMapper;
import com.traffic.Mapper.UpdateMapper;
import com.traffic.pojo.Car;
import com.traffic.pojo.Factory;
import com.traffic.pojo.ReqBody;
import com.traffic.utils.GetColumnNum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class InitService {

    @Autowired
    private InitMapper initMapper;
    @Autowired
    private UpdateMapper updateMapper;
    @Autowired
    private GetColumnNum getColumnNum;
    @Autowired
    private UpdateService updateService;

    // 使用 SimpleDateFormat 格式化时间戳
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public void initTime(String tableName,int low) {
        int columnNum=getColumnNum.getCount(tableName);
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

    public void initCar(){
        int columnNum=getColumnNum.getCount("car");
        List<Integer> cars=new ArrayList<>();
        for (int i = 1; i <= columnNum; i++) {
            cars.add(i);
        }
        initMapper.changeCarStatus(cars,1);
    }

    public List<Object> backFactoryAndCar(ReqBody reqBody){
        List<Object> res=new ArrayList<>();
        List<Integer> cars=new ArrayList<>();

        Car[] carschoose=initMapper.chooseCar();
        if(carschoose.length==0){
            res.add("当前无空闲车辆。");
            return res;
        }
        Factory[] factorieschooose=initMapper.chooseFactory(reqBody.factoryName);
        if(factorieschooose.length==0){
            res.add("当前无工厂需要运输。");
            return res;
        }

        int min=Math.min(carschoose.length,factorieschooose.length);
        for (int i = 0; i < min; i++) {
            cars.add(carschoose[i].id);
        }

        if(carschoose.length<factorieschooose.length){
            factorieschooose= Arrays.copyOfRange(factorieschooose,0,min);
        }else{
            carschoose=Arrays.copyOfRange(carschoose,0,min);
        }

        initMapper.changeCarStatus(cars,0);

        res.add(factorieschooose);
        res.add(carschoose);
        return res;
    }
}
