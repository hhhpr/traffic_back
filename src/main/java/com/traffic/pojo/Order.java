package com.traffic.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Order {
    public int id;
    public int state; // 0表示没有接单,1表示正在运输,2表示运输完成
    public Timestamp generationtime;
    public int startfactoryid;
    public String startfactoryname;
    public double startlatitude;
    public double startlongitude;
    public int endfactoryid;
    public String endfactoryname;
    public double endlatitude;
    public double endlongitude;
    public int carid;
    public String goodid;
    public int goodcount;
}
