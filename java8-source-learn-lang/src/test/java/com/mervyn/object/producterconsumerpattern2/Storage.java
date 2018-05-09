package com.mervyn.object.producterconsumerpattern2;

import com.mervyn.object.producterconsumerpattern.*;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
/**
 * @author hexinxin
 * @Date: 2018/5/2
 * @Time: 19:13
 * @Description:
 */
public class Storage {
    private BlockingQueue<Egg> blockingQueue = new LinkedBlockingDeque<Egg>(100);

    public void add (Egg egg) throws InterruptedException {
        System.out.println("把编号为：" + egg.getId() + "的鸡蛋放入仓库");
        blockingQueue.put(egg);
    }

    public Egg get () throws InterruptedException {
        Egg egg = blockingQueue.take();
        System.out.println("从仓库中取出编号为：" + egg.getId() + "的鸡蛋");
        return egg;
    }


}
