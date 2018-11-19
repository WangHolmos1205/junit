package com.wangfan.example.junit.externalservice.spy;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

public class CircumferenceServiceTest {

    /**
     * 测试间谍与mock(伪造对象)类似，通常我们认为mock是一种特殊的spy，规定好某种特定场景下的特定行为的对象，
     * spy的对象是预期真的被执行，比如数据库操作是真的被操作，但是我们需要在不改变代码结构的情况下，了解到一些行为是否真的按预期发生。
     */

    @Test
    public void updateNum() {
        test(1,1,1);
    }

    private void test(int numberOne, int numberTwo, int circumference){

        SpyServiceToAnswer spyServiceToAnswer = new SpyServiceToAnswer();
        SpyServiceToSave spyServiceToSave = new SpyServiceToSave();
        spyServiceToAnswer.updateNum(numberOne, numberTwo);
        spyServiceToSave.updateNum(numberOne, numberTwo);
        // 证明计算值与预期相符
        assert circumference == spyServiceToAnswer.getAnswer();
        // 证明确实调用了存储方法
        assert Boolean.TRUE.equals(spyServiceToSave.getCalled());
    }

    private class SpyServiceToSave extends CircumferenceService{

        private Boolean called = false;

        public boolean getCalled() {
            return this.called;
        }

        @Override
        public void saveNum(){
            this.called = true;
        }

    }

    private class SpyServiceToAnswer extends CircumferenceService{

        private double answer;

        public double getAnswer() {
            return this.answer;
        }
        @Override
        public void updateNum(int numberOne, int numberTwo) {
            double circumferenceRatio = 3.141592653;
            this.answer= circumferenceRatio * (numberOne + numberTwo);
        }

    }


}