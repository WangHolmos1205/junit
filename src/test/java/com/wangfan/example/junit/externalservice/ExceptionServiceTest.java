package com.wangfan.example.junit.externalservice;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 特殊测试情况范例
 * @auther 王帆
 */
@SpringBootTest
@RunWith(SpringRunner.class)
/**
 * 指定测试顺序
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExceptionServiceTest {

    /**
     * 当我们希望本次测试应该抛出某种异常时，可以使用expected来设置预期,如果未能抛出，则测试不通过
     */
    @Test(expected = NullPointerException.class)
    public void getException() {
        System.out.println(1);
        throw new NullPointerException();
    }

    /**
     * 当我们希望测试代码运行时长，可以使用timeout
     * @throws Exception
     */
    @Test(timeout = 5000)
    public void getTimeOut() throws Exception{
        System.out.println(2);
        Thread.sleep(4000);
    }

    /**
     * 某些不想生效的错误遗旧测试，可以用@Ignore暂时跳过，但是还是建议如果有时间修复一下
     */
    @Ignore
    @Test(expected = NullPointerException.class)
    public void getIgnore() {
        System.out.println(3);
        throw new NullPointerException();
    }
}