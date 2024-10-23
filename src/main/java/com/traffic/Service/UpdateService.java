package com.traffic.Service;

import com.traffic.Mapper.UpdateMapper;
import com.traffic.Mapper.Utils;
import com.traffic.pojo.Result;
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
    @Autowired
    private Utils utils;
    public void updateTime(UpdateBody updateBody,boolean flag,int id,int clas) {
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
        }
    }

    public void updateTime(UpdateBody updateBody) {
        updateTime(updateBody, true,-1,updateBody.clas); // 默认值为 false
    }

    public void updateIsReady(UpdateBody updateBody) {
        updateMapper.updateCar(updateBody.carId,updateBody.longitude,updateBody.latitude,1);
    }

    public UpdateBody getGoods(UpdateBody updateBody) {
        int RowCount;
        Random random=new Random();
        if(updateBody.clas==1){
            RowCount=utils.getRowCount("furniturefactory");
            return updateMapper.getFactoryGoods("furniturefactory", random.nextInt(RowCount)+1)[0];
        }else {
            RowCount=utils.getRowCount("furniture");
            return updateMapper.getFactoryGoods("furniture", random.nextInt(RowCount)+1)[0];
        }
    }
}
