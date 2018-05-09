package com.mervyn.object.producterconsumerpattern2;



import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/5/2
 * @Time: 19:06
 * @Description:
 */
public class ProducterConsumerManagement {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Storage storage = new Storage();
        Producter producterTask = new Producter(storage);
        Consumer consumerTask = new Consumer(storage);
        executorService.execute(producterTask);
        executorService.execute(consumerTask);
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
