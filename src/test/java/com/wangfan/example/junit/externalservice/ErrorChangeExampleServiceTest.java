package com.wangfan.example.junit.externalservice;

import com.wangfan.example.junit.service.ExampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ErrorChangeExampleServiceTest {

    @Mock
    private ExampleService exampleServiceMock;

    private ErrorExampleService errorExampleService;

    @Test
    public void getStarLevel() {

        /**
         * 说心里话这个单元测试没法写，头疼。
         * 问题1、逻辑很混乱，多种规则叠加，导致了复杂度成倍叠加增长，
         *    1.1 人容易糊涂，单元测试成倍增长，写单元测试比源码都累，容易开发人员偷懒，这个最难受。
         *    1.2 单元测试中，只能整体逻辑混测，当出现测试不通过之后，定位错误原因，错误范围较麻烦。
         * 问题2、数据来源复杂，这个其实很正常，比上一个好多了。
         *   2.1 可能引用了其他项目，如果想本地测试，需要启动其他项目，做不到，要不你上dev远程测试一下，
         *        然后就可能大家抢dev用，每个人的分支不同啊，合并一个bug可能相互影响，而且一套发布时间成本也不小。
         *   2.2 有的来源还是硬件信号，是不是还得找个硬件，然后模拟返回值，甚至是边界值，
         *        测试怎么测得我不知道，反正我估计开发就不测试了，或者说不覆盖测试了，通了，然后等测试找我吧。
         * 问题2单元测试可以解决，问题1后面再说吧
         */

        /**
         * 这个测试存在坏味道，过度断言，一个测试预期实现的东西太多了，既验证行驶里程，又验证生产年份，太容易测试不通过，
         * 而测试不通过之后，我们可能需要重新审阅整个代码，很麻烦和浪费时间。
         */
        mockAndTestOneSituation(1, 102400, Boolean.FALSE, 4);
        mockAndTestOneSituation(2, 102400, Boolean.FALSE, 3);
        mockAndTestOneSituation(3, 102400, Boolean.FALSE, 2);

        mockAndTestOneSituation(1, 204800, Boolean.FALSE, 3);
        mockAndTestOneSituation(2, 204800, Boolean.FALSE, 2);
        mockAndTestOneSituation(3, 204800, Boolean.FALSE, 1);

        mockAndTestOneSituation(1, 307200, Boolean.FALSE, 2);
        mockAndTestOneSituation(2, 307200, Boolean.FALSE, 1);
        mockAndTestOneSituation(3, 307200, Boolean.FALSE, 0);

        mockAndTestOneSituation(2, 0, Boolean.TRUE, 5);
        mockAndTestOneSituation(4, 0, Boolean.TRUE, 4);
        mockAndTestOneSituation(6, 0, Boolean.TRUE, 3);

        /**
         * 魔法数字有时候也是测试的坏味道，以下这个仅供参考，因为idea一般其实会显示出入参名称，如果命名合适，
         * 其实我觉得比这个看起来舒服
         */
        mockAndTestOneSituation(driverYear(6), driveMileage(0), isNewCar(Boolean.TRUE), hopeStartLevel(3));
    }

    @Test
    public void testDriveMileage(){
        mockAndTestOneSituation(0, 102400, isNewCar(Boolean.FALSE), 4);
        //mockAndTestOneSituation(2, 102400, isNewCar(Boolean.FALSE), 3);
        //mockAndTestOneSituation(3, 102400, isNewCar(Boolean.FALSE), 2);

        mockAndTestOneSituation(0, 204800, isNewCar(Boolean.FALSE), 3);
        //mockAndTestOneSituation(2, 204800, isNewCar(Boolean.FALSE), 2);
        //mockAndTestOneSituation(3, 204800, isNewCar(Boolean.FALSE), 1);

        mockAndTestOneSituation(0, 307200, isNewCar(Boolean.FALSE), 2);
        //mockAndTestOneSituation(2, 307200, isNewCar(Boolean.FALSE), 1);
        //mockAndTestOneSituation(3, 307200, isNewCar(Boolean.FALSE), 0);

        mockAndTestOneSituation(0, 307200, isNewCar(Boolean.TRUE), 5);

    }

    @Test
    public void testDriverYear(){
        // 新车
        mockAndTestOneSituation(2, 0, isNewCar(Boolean.TRUE), 5);
        mockAndTestOneSituation(4, 0, isNewCar(Boolean.TRUE), 4);
        mockAndTestOneSituation(6, 0, isNewCar(Boolean.TRUE), 3);

        // 旧车
        mockAndTestOneSituation(1, 0, isNewCar(Boolean.FALSE), 4);
        mockAndTestOneSituation(2, 0, isNewCar(Boolean.FALSE), 3);
        mockAndTestOneSituation(3, 0, isNewCar(Boolean.FALSE), 2);
    }
    @Before
    public void addMockToErrorExampleService(){
        // 无论传入任何值，都返回指定值
        // 使用mock控制
        errorExampleService = new ErrorExampleService(exampleServiceMock);
    }

    /**
     * mock返回情况并测试预期，重复的测试方法提纯，便于重复测试结果更直接，增加代码可读性
     *
     * @param driverYear 假定年份
     * @param driveMileage 假定行驶公里
     * @param isNew 假定是否新车
     * @param hopeStartLevel 预期结果
     */
    private void mockAndTestOneSituation(Integer driverYear, Integer driveMileage,
                                         Boolean isNew, Integer hopeStartLevel){

        /**
         *  这里因为源码都是一个来源，如果按照假设情况多个来源，需要mock每一个对象,很麻烦。
         *  而且测试范围过大，不利于定位问题，即使发现了错误情况，也不能快速定位是因为年份、里程还是新车计算逻辑错误导致的。
         */
        // 无论传入任何值，都返回指定值
        when(exampleServiceMock.getDrivceYearByGuid(anyString())).thenReturn(driverYear);
        when(exampleServiceMock.getDrivceMileageByGuid(anyString())).thenReturn(driveMileage);
        when(exampleServiceMock.checkNewCar(anyString())).thenReturn(isNew);
        // 判定结果是否相等
        int level = errorExampleService.getStarLevel(anyString());
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(driverYear).append(":")
                .append(driveMileage).append(":")
                .append(isNew).append(":")
                .append(hopeStartLevel).append(":")
                .append(level);
        System.out.println(stringBuffer.toString());
        assert hopeStartLevel.equals(errorExampleService.getStarLevel(anyString()));
    }

    private Integer driverYear(Integer driverYear){
        return driverYear;
    }

    private Integer driveMileage(Integer driveMileage){
        return driveMileage;
    }

    private Boolean isNewCar(Boolean isNew){
        return isNew;
    }


    private Integer hopeStartLevel(Integer hopeStartLevel){
        return hopeStartLevel;
    }
}