package com.mervyn.object;

import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/21
 * @Time: 9:35
 * @Description: 使用Object类的wait方法和notifyAll方法实现汽车的打蜡和抛光
 */
public class Car {
    private boolean waxOn = false;

    public synchronized void waxed() throws InterruptedException {
        System.out.println("打蜡");
        TimeUnit.MICROSECONDS.sleep(200);
        waxOn = true;
        notifyAll();
    }
    public synchronized void waitForWaxing() throws InterruptedException {
        while (waxOn != false) {
            System.out.println("等待打蜡...");
            wait();
        }
    }


    public synchronized void buffed() throws InterruptedException {
        System.out.println("抛光");
        TimeUnit.MICROSECONDS.sleep(200);
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while (waxOn != true) {
            System.out.println("等待抛光...");
            wait();
        }
    }
}
