package com.mervyn.object;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/23
 * @Time: 9:53
 * @Description: 打蜡抛光
 */
public class WaxOnMatic {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Car car = new Car();
        executorService.execute(new WaxOn(car));
        executorService.execute(new WaxOff(car));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
