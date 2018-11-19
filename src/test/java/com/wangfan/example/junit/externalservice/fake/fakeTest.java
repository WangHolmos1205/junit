package com.wangfan.example.junit.externalservice.fake;

import com.wangfan.example.junit.model.CarInfo;

import java.util.HashMap;
import java.util.Map;

public class fakeTest {

    private class fakeCarKepper extends CarKepperImpl{
        Map<String, CarInfo> mapCar = new HashMap<>();

        @Override
        public void saveUser(CarInfo carInfo){
            // 模拟数据库存储
            mapCar.put(carInfo.getGuid(), carInfo);
        }

        @Override
        public void getUserByGuid(String guid){
            // 模拟数据库读取
            mapCar.get(guid);

        }
    }
}
