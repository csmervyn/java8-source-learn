package com.mervyn.object.producterconsumerpattern1;



import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author hexinxin
 * @Date: 2018/5/2
 * @Time: 18:52
 * @Description:
 */
public class Producter implements Runnable{
    private Storage storage;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public Producter(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Egg egg = new Egg(atomicInteger.getAndIncrement());
                storage.add(egg);
            }
        } catch (InterruptedException e) {
            System.out.println("鸡蛋生产者被中断");
            e.printStackTrace();
        }finally {
            System.out.println("停止鸡蛋生产者的任务");
        }
    }
}
