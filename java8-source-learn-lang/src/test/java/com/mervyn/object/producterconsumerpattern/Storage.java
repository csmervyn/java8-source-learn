package com.mervyn.object.producterconsumerpattern;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 15:19
 * @Description:
 */
public class Storage {
    private final int MAX_SIZE = 100;
    private List<Egg> eggStorage = new LinkedList<Egg>();

    public synchronized Egg get() throws InterruptedException {
        while(eggStorage.size() <= 0) {
            System.out.println("仓库内暂时没有鸡蛋，等待进货...");
            wait();
        }
        Egg egg = (Egg)eggStorage.remove(0);
        TimeUnit.MICROSECONDS.sleep(200);
        System.out.println("从仓库中取出编号为：" + egg.getId() + "的鸡蛋");
        notifyAll();
        return egg;
    }

    public synchronized void add(Egg egg) throws InterruptedException {
        while (eggStorage.size() >= MAX_SIZE) {
            System.out.println("等待把鸡蛋放入仓库...");
            wait();
        }
        System.out.println("把编号为：" + egg.getId() + "的鸡蛋放入仓库");
        eggStorage.add(egg);
        TimeUnit.MICROSECONDS.sleep(200);
        notifyAll();
    }
}
