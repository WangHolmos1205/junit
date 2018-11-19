package com.wangfan.example.junit.externalservice;

import com.wangfan.example.junit.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorExampleService {

    private static final int MAX_STAR_LEVEL = 5;

    private static final int EXCHANGE_DRIVEMILEAGE = 1024;

    private static final int FIVE_YEAR = 5;

    private static final int THREE_YEAR = 3;

    private final ExampleService exampleService;

    @Autowired
    ErrorExampleService(ExampleService exampleService){
        this.exampleService = exampleService;
    }

    /**
     * 描述一下需求内容，假设，你数据库存储了一辆车是否是新车，一个辅助项目可以告诉你车出场几年，
     * 然后从硬件可以获取到这辆车的行驶里程，现在你需要判断该车星级，初始星级5星，开始扣星
     * 扣星逻辑如下：
     * 1、如果不是新车，那么出场年份双倍计算，既旧车1年按照两年计算。
     * 2、5年以上扣2星，3-5年扣1星
     * 3、新车不计算行驶里程扣星，旧车(里程数/1024)>200，扣3星，（里程数/1024）在100-300之间扣2星，
     * （里程数/1024）小于100扣1星
     */

    /**
     * 评定汽车星级
     * @return
     */
    public Integer getStarLevel(String carGuid){
        // 初始星级
        Integer starLevel = MAX_STAR_LEVEL;

        // 这个可能是从其他项目或硬件信号查的，这里就是代表数据来源一，几年前的车，不满一年为0
        int year = exampleService.getDrivceYearByGuid(carGuid);
        // 这个可能是从其他项目查的，这里就是代表数据来源二，是否是新车
        Boolean isNew = exampleService.checkNewCar(carGuid);
        // 计算年份星级
        if(!isNew){
            // 不是新车，双倍折旧
            year *= 2;
        }
        // 五年以上扣2星，3年以上扣1星 篇幅限制我就先造了这两种规则
        if(year > FIVE_YEAR){
            starLevel -= 2;
        }else if(year > THREE_YEAR){
            starLevel -= 1;
        }

        //计算距离星级
        if(!isNew){
            // 从机器读取信号，这个代表数据来源三
            Integer driveMileage = exampleService.getDrivceMileageByGuid(carGuid);
            //信号值需要单位转换 甚至需要一些逻辑运算，篇幅限制一行表示还需要其他逻辑运算
            double changeMileage = driveMileage/EXCHANGE_DRIVEMILEAGE;
            // 行驶200以上扣2星，行驶100以上扣1星 篇幅限制我就先造了这两种规则
            if(changeMileage > 200){
                starLevel -= 3;
            }else if(changeMileage > 100){
                starLevel -= 2;
            }else {
                starLevel -= 1;
            }
        }
        return starLevel;
    }
}
