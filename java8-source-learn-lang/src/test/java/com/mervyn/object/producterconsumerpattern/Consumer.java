package com.mervyn.object.producterconsumerpattern;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 16:21
 * @Description: 鸡蛋消费者
 */
public class Consumer implements Runnable{
    private Storage storage;

    public Consumer(Storage storage) {
        this.storage = storage;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Egg egg = storage.get();
            }
        } catch (InterruptedException e) {
            System.out.println("鸡蛋消费者被中断");
            e.printStackTrace();
        }finally {
            System.out.println("停止鸡蛋消费者任务");
        }
    }
}
