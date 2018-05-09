package com.mervyn.object.producterconsumerpattern1;



import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hexinxin
 * @Date: 2018/5/2
 * @Time: 18:05
 * @Description:
 */
public class Storage {
    private final int MAX_SIZE = 100;
    private List<Egg> eggStorage = new LinkedList<Egg>();
    private Lock lock = new ReentrantLock();
    private Condition producerCondition = lock.newCondition();
    private Condition consumerCondition = lock.newCondition();

    public void add (Egg egg) throws InterruptedException {
        lock.lock();
        try {
            while (eggStorage.size() >= MAX_SIZE) {
                System.out.println("等待把鸡蛋放入仓库...");
                producerCondition.await();
            }
            System.out.println("把编号为：" + egg.getId() + "的鸡蛋放入仓库");
            eggStorage.add(egg);
            TimeUnit.MICROSECONDS.sleep(200);
            consumerCondition.signalAll();
        }finally {
            lock.unlock();
        }
    }

    public Egg get() throws InterruptedException {
        lock.lock();
        Egg egg = null;
        try {
            while (eggStorage.isEmpty()) {
                System.out.println("仓库内暂时没有鸡蛋，等待进货...");
                consumerCondition.await();
            }
            egg = (Egg) eggStorage.remove(0);
            TimeUnit.MICROSECONDS.sleep(200);
            System.out.println("从仓库中取出编号为：" + egg.getId() + "的鸡蛋");
            producerCondition.signalAll();
        } finally {
            lock.unlock();
        }
        return egg;
    }
}
