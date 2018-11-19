package com.wangfan.example.junit.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class CarInfo {

    /**
     * 编号
     */
    private String guid;

    /**
     * 名称
     */
    private String name;

    /**
     * 开了几年
     */
    private Integer driverYear;

    /**
     * 行驶里程
     */
    private Integer driveMileage;

    private double changeMileage;

    /**
     * 是否是新车
     */
    private boolean isNew;
}
