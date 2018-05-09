package com.mervyn.object;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 11:21
 * @Description:
 */
public class PrintOneTask implements Runnable{
    private NumberPrint numberPrint;

    public PrintOneTask(NumberPrint numberPrint) {
        this.numberPrint = numberPrint;
    }

    public void run() {
        try {
            while (!Thread.interrupted()){
                numberPrint.printOne();
            }
        } catch (InterruptedException e) {
            System.out.println("打印1任务被中断");
            e.printStackTrace();
        }finally {
            System.out.println("停止打印1任务");
        }
    }
}
