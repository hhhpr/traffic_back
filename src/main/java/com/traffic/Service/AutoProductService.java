package com.traffic.Service;

import com.traffic.Mapper.AutoProductMapper;
import com.traffic.pojo.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AutoProductService {
    //进行自动生产，首先查询工厂库存，对现有原料存量小于总容量40%且总库存小于总容量的80%的工厂进行生产
    @Autowired
    private AutoProductMapper autoProductMapper;


    private int proTime=15;//表示本轮生产的时间，15s
    public void AutoProduct(String tableName){
        //首先查询数据库，找出现有原料存量小于总容量40%且总库存小于总容量的80%的工厂
        Factory[] enableProductFactories = autoProductMapper.enableToProductFactory(tableName);

        //对返回的工厂进行生产
        if(enableProductFactories.length!=0){
            //对于一级工厂，直接生产，不需要考虑原料
            if(enableProductFactories[0].clas==1){
                int newGoodsInventory=0;
                for(int i=0;i<enableProductFactories.length;i++){
                    newGoodsInventory=proTime * enableProductFactories[i].speedRate+enableProductFactories[i].goodsInventory;
                    if(newGoodsInventory<enableProductFactories[i].totalInventory){
                        enableProductFactories[i].goodsInventory=newGoodsInventory;
                    }
                }
                //更新
                System.out.println("更新了"+tableName+"的库存");
                autoProductMapper.updateInventory(enableProductFactories,tableName);
            }else{
                int maxProNum=0;//表示本轮可以生产的产品数量，受原料和总容量制约
                int newGoodsInventory=0;
                int rawConsumn;//表示本轮生产消耗的原料
                for(int i=0;i<enableProductFactories.length;i++){

                    //首先计算现有原料最多支持生产多少产品,原料库存*原料转化比transRate
                    maxProNum =(int) (enableProductFactories[i].rawInventory * enableProductFactories[i].transRate);

                    //再计算当前库存最多支持生产多少产品
                    maxProNum=Math.min(maxProNum,
                            enableProductFactories[i].totalInventory-enableProductFactories[i].goodsInventory-enableProductFactories[i].rawInventory);

                    //最后计算根据本轮时间，本轮可以生产多少产品
                    maxProNum=Math.min(maxProNum,(int)(proTime * enableProductFactories[i].speedRate));

                    //根据本轮的生产情况，修改库存
                    enableProductFactories[i].rawInventory -= (int)(maxProNum / enableProductFactories[i].transRate);
                    enableProductFactories[i].goodsInventory += maxProNum;
                }

                //更新
                System.out.println("更新了"+tableName+"的库存");
                autoProductMapper.updateInventory(enableProductFactories,tableName);
            }
        }else{
            return;
        }
    }

}
