package com.mervyn.object;

import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/23
 * @Time: 9:43
 * @Description: 给汽车抛光任务
 */
public class WaxOff implements Runnable{
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    public void run() {
        try {
            while(!Thread.interrupted()) {
                car.waitForBuffing();
                car.buffed();
            }
        } catch (InterruptedException e) {
            System.out.println("抛光任务被打断");
            e.printStackTrace();
        }
        System.out.println("停止抛光任务");

    }
}
