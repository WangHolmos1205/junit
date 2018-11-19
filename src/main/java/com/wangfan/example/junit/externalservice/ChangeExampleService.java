package com.wangfan.example.junit.externalservice;

import com.wangfan.example.junit.model.CarInfo;
import com.wangfan.example.junit.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ChangeExampleService {

    private static final int MAX_STAR_LEVEL = 5;

    private static final int EXCHANGE_DRIVEMILEAGE = 1024;

    private static final int FIVE_YEAR = 5;

    private static final int THREE_YEAR = 3;

    private final ExampleService exampleService;

    @Autowired
    ChangeExampleService(ExampleService exampleService){
        this.exampleService = exampleService;
    }

    /**
     * 评定汽车星级
     * @return
     */
    public Integer getStarLevel(String carGuid){
        // 初始星级
        Integer starLevel = MAX_STAR_LEVEL;

        // 汽车情况数据获取
        CarInfo carInfo = getCarInfo(carGuid);

        // 根据年份星级打分
        starLevel -= getStartLevelByDriverYear(carInfo.getDriverYear(), carInfo.isNew());

        // 根据行驶路程打分
        starLevel -= getStartLevelByDriverMileage(carInfo.getChangeMileage(), carInfo.isNew());
        return starLevel;
    }

    /**
     * 返回查询信息
     * @param carGuid
     * @return
     */
    private CarInfo getCarInfo(String carGuid){
        int driveYear = exampleService.getDrivceYearByGuid(carGuid);
        Boolean isNew = exampleService.checkNewCar(carGuid);
        Integer driveMileage = 0;
        double changeMileage = 0;
        if(!isNew) {
            driveMileage = exampleService.getDrivceMileageByGuid(carGuid);
            changeMileage = driveMileage/EXCHANGE_DRIVEMILEAGE;
        }

        return CarInfo.builder().guid(carGuid)
                .driverYear(driveYear).changeMileage(changeMileage)
                .isNew(isNew).build();
    }

    /**
     * 得到根据开车年份计算的星级
     * @param driveYear
     * @param isNew
     * @return
     */
    public Integer getStartLevelByDriverYear(Integer driveYear , Boolean isNew){
        int starLevelByYear = 0;
        if(!isNew){
            // 不是新车，双倍折旧
            driveYear *= 2;
        }
        // 五年以上扣2星，3年以上扣1星 篇幅限制我就先造了这两种规则
        if(driveYear > FIVE_YEAR){
            starLevelByYear -= 2;
        }else if(driveYear > THREE_YEAR){
            starLevelByYear -= 1;
        }
        return starLevelByYear;
    }

    /**
     * 得到根据开车里程计算的星级
     * @param changeMileage
     * @param isNew
     * @return
     */
    public Integer getStartLevelByDriverMileage(double changeMileage , Boolean isNew){
        int starLevelByMileage= 0;
        //计算距离星级
        if(!isNew){
            // 行驶200以上扣2星，行驶100以上扣1星 篇幅限制我就先造了这两种规则
            if(changeMileage > 200){
                starLevelByMileage -= 3;
            }else if(changeMileage > 100){
                starLevelByMileage -= 2;
            }else {
                starLevelByMileage -= 1;
            }
        }
        return starLevelByMileage;
    }
}
