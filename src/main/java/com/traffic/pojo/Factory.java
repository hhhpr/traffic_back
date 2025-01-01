package com.traffic.pojo;

import lombok.Data;

@Data
public class Factory {
    public int id;
    public String name;
    public String address;
    public double latitude;
    public double longitude;
    public int clas;

    public String goodsID;
    public int rawInventory;
    public int goodsInventory;
    public int totalInventory;
    public double transRate;
    public int speedRate;
    public String rawID;
}
