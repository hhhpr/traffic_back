package com.traffic.Service;

import com.traffic.Mapper.UpdateMapper;
import com.traffic.pojo.UpdateBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Random;

@Service
public class UpdateService {


    @Autowired
    private UpdateMapper updateMapper;
    public void updateTimeAndIsready(UpdateBody updateBody,boolean flag,int id,int clas) {
        String factoryName;
        if(clas==1) {
            factoryName = "woodfactory1";
        } else if (clas==2) {
            factoryName="furniturefactory";
        }else{
            factoryName="furniture";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Random random=new Random();
        String ranNum= sdf.format(Timestamp.from(Instant.now().plusSeconds(random.nextInt(41)+15)));
        if(!flag){
            updateMapper.updateTime(id,ranNum,factoryName);
        }else{
            updateMapper.updateTime(updateBody.factoryId, ranNum,factoryName);
            updateMapper.updateCar(updateBody.carId,updateBody.longitude,updateBody.latitude,1);
        }
    }

    public void updateTimeAndIsready(UpdateBody updateBody) {
        updateTimeAndIsready(updateBody, true,-1,updateBody.clas); // 默认值为 false
    }
}
