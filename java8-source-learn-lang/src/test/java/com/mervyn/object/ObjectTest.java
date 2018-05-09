package com.mervyn.object;

import org.junit.Test;

/**
 * @author hexinxin
 * @Date: 2018/5/9
 * @Time: 10:06
 * @Description: 测试Object类的方法
 */
public class ObjectTest {
    @Test
    public void testWait() {
        synchronized(this){
            try {
                wait(1);
                System.out.println("time out 1000 milliseconds");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
