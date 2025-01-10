package com.traffic.Service;

import com.traffic.Mapper.CarMapper;
import com.traffic.pojo.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CarStateService {

    @Autowired
    private CarMapper carMapper;
    //自动更新car的state，0表示可以接单，1表示拒绝接单，2表示车辆保养，无法接单
    public void changeCarService(){
        Car[] cars=carMapper.getCar();
        Random random=new Random();
        for (Car car: cars){
            if(car.isready==1&&car.state!=0){//如果车辆空闲且不是可接单状态，就改为可接单状态
                carMapper.setCarState(car.id,0);
                System.out.println("车辆"+car.id+"state改为0");
            }else if(car.isready==1&&car.state==0){//如果车辆空闲且是可接单装填，就以一定概率转换为拒绝接单或车辆保养状态
                int rand=random.nextInt(0,100);
                if(rand>=80){
                    if(rand>=90){
                        carMapper.setCarState(car.id,1);
                    }else{
                        carMapper.setCarState(car.id, 2);
                    }
                    System.out.println("车辆"+car.id+"state现在无法接单");
                }
            }
        }
    }
}
