package com.traffic.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Marker {
    String id;
    String name;
    String address;
    double latitude;
    double longitude;
    int clas;
    String goodsID;
    int rawInventory;
    int goodsInventory;
    int totalInventory;
    double transRate;
    double speedRate;
    String rawID;

}
