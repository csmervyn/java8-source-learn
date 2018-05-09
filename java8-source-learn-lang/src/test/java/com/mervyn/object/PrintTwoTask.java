package com.mervyn.object;

/**
 * @author hexinxin
 * @Date: 2018/4/24
 * @Time: 11:26
 * @Description:
 */
public class PrintTwoTask implements Runnable{
    private NumberPrint numberPrint;

    public PrintTwoTask(NumberPrint numberPrint) {
        this.numberPrint = numberPrint;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                numberPrint.printTwo();
            }
        } catch (InterruptedException e) {
            System.out.println("打印2任务被中断");
            e.printStackTrace();
        }finally {
            System.out.println("停止打印2任务");
        }
    }
}
