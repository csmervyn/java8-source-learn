package com.mervyn.object;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 11:30
 * @Description:
 */
public class PrintOneOrTwoMangement {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        NumberPrint numberPrint = new NumberPrint();
        executorService.execute(new PrintOneTask(numberPrint));
        executorService.execute(new PrintTwoTask(numberPrint));
        TimeUnit.SECONDS.sleep(5);
        executorService.shutdownNow();
    }
}
