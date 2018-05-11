package com.mervyn.object;

import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/21
 * @Time: 10:06
 * @Description: 给汽车打蜡任务
 */
public class WaxOn implements Runnable{
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    public void run() {
        try {
            while(!Thread.interrupted()) {
                car.waxed();
                car.waitForWaxing();
            }
        } catch (InterruptedException e) {
            System.out.println("打蜡任务被中断");
            e.printStackTrace();
        }
        System.out.println("停止打蜡任务");
    }
}
