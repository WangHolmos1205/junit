package com.wangfan.example.junit.service;

import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    /**
     * 返回行驶年份信号点的值
     * @return
     */
    public Integer getDrivceYearByGuid(String carGuid){
        return 0;
    }

    /**
     * 返回行驶距离（需转换运算）
     * @return
     */
    public Integer getDrivceMileageByGuid(String carGuid){
        return 2350051;
    }

    /**
     * 判断是否是新车
     * @param carGuid
     * @return
     */
    public Boolean checkNewCar(String carGuid){
        return Boolean.FALSE;
    }
}
