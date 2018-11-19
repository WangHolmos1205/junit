package com.wangfan.example.junit.externalservice;

import com.wangfan.example.junit.service.ExampleService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.anyDouble;


@SpringBootTest
@RunWith(SpringRunner.class)
public class ChangeExampleServiceTest {

    @Autowired
    private ExampleService exampleService;
    private ChangeExampleService changeExampleService;

    @Test
    public void getStarLevel() {
        // 流程代码单元测试的价值不大，postman走一下流程测试，确定能跑通一次即可。
    }

    /**
     * 对于不通逻辑的测试应该明确分开，让单元测试变得更可读，这对于某次出现生产问题或者你情绪比较着急的时候，单元测试的可读性
     * 非常的重要，代码的阅读，快速理解一个单元测试的意图，对于我们来说，有时候是正确了救命的几分钟，而且更轻松一点不是更好。
     */
    @Test
    public void getStartLevelByDriverYear() {
        assert changeExampleService.getStartLevelByDriverYear(3,Boolean.FALSE)
                .equals(-2);
        assert changeExampleService.getStartLevelByDriverYear(2,Boolean.FALSE)
                .equals(-1);
        assert changeExampleService.getStartLevelByDriverYear(1,Boolean.FALSE)
                .equals(0);

        /**
         * 如果源码更加复杂，这个测试可以考虑再拆分，现在其实上半段是在测试旧车的情况，下半段在测试新策的情况，适当的拆分或注释
         * 可以降低我们判断问题时花费的时间
         */
        assert changeExampleService.getStartLevelByDriverYear(6,Boolean.TRUE)
                .equals(-2);
        assert changeExampleService.getStartLevelByDriverYear(4,Boolean.TRUE)
                .equals(-1);
        assert changeExampleService.getStartLevelByDriverYear(2,Boolean.TRUE)
                .equals(0);
    }

    @Test
    public void getStartLevelByDriverMileage() {
        assert changeExampleService.getStartLevelByDriverMileage(300, Boolean.FALSE)
                .equals(-3);
        assert changeExampleService.getStartLevelByDriverMileage(200, Boolean.FALSE)
                .equals(-2);
        assert changeExampleService.getStartLevelByDriverMileage(100, Boolean.FALSE)
                .equals(-1);
        /**
         * 这里的测试看起来有点怪，其实是因为新车，不会真正读取公里数，写完这个测试一周的我，忘记了这个逻辑，
         * 还需要重新回到方法里看为什么，这个可能就是一个单元测试的坏味道，测试没有明确体现出测试的逻辑
         */
        assert changeExampleService.getStartLevelByDriverMileage(300, Boolean.TRUE)
                .equals(0);

        /**
         * 这个代表任何值都不扣分，是不是可能更清楚一点
         */
        assert changeExampleService.getStartLevelByDriverMileage(anyDouble(), Boolean.TRUE)
                .equals(0);
    }

    @Before
    public void createChangeExampleService(){
        changeExampleService = new ChangeExampleService(exampleService);
    }
}