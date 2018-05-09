package com.mervyn.object;

import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 11:12
 * @Description: 数字打印
 */
public class NumberPrint {
    private boolean flag = false;
    public synchronized void printOne() throws InterruptedException {
        while (flag == true) {
            wait();
        }
        TimeUnit.MICROSECONDS.sleep(200);
        System.out.println("1");
        flag = true;
        notifyAll();
    }

    public synchronized void printTwo() throws InterruptedException {
        while (flag == false) {
            wait();
        }
        TimeUnit.MICROSECONDS.sleep(200);
        System.out.println("2");
        flag = false;
        notifyAll();
    }
}
