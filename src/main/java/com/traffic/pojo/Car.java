package com.traffic.pojo;

import lombok.Data;

import javax.swing.plaf.PanelUI;

@Data
public class Car {
    public int id;
    public String type;
    public int load;
    public double latitude;
    public double longitude;
    public int isready;
    public int state;
}
