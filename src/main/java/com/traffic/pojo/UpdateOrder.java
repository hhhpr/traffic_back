package com.traffic.pojo;

import lombok.Data;

@Data
public class UpdateOrder {
    public int orderId;
    public int state; // 1为取货完成；2为送货完成
}
