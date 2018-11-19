package com.wangfan.example.junit.externalservice.spy;

public class CircumferenceService {

    private static double circumference;

    public void updateNum(int numberOne, int numberTwo){
        double circumferenceRatio = 3.141592653;
        circumference = circumferenceRatio * (numberOne + numberTwo);
        if(circumference > 0) {
            saveNum();
        }
    }

    public void saveNum(){
        // 某些操作，如发送邮件、数据库存储
    }
}
