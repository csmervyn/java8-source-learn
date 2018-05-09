package com.mervyn.object.producterconsumerpattern;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 16:26
 * @Description:
 */
public class ProducterConsumerManagement {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Storage storage = new Storage();
        executorService.execute(new Producter(storage));
        executorService.execute(new Consumer(storage));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
