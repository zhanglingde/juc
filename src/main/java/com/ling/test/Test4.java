package com.ling.test;

/**
 * 输出内容     等待标记    下一个标记
 *    a          1          2
 *    b          2          3
 *    c          3          1
 */
public class Test4 {

    public static void main(String[] args) {
        WaitNotify waitNotify = new WaitNotify(1, 5);
        new Thread(()->{
            waitNotify.print("A",1,2);
        },"t1").start();
        new Thread(()->{
            waitNotify.print("B",2,3);
        },"t2").start();
        new Thread(()->{
            waitNotify.print("C",3,1);
        },"t3").start();
    }
}

class WaitNotify{
    // 等待标记
    private int flag;
    // 循环次数
    private int loopNumber;

    public WaitNotify(int flag, int loopNumber) {
        this.flag = flag;
        this.loopNumber = loopNumber;
    }

    /**
     *
     * @param str 打印内容
     * @param waitFlag 等待标记
     * @param nextFlag 下一个标记
     */
    public void print(String str, int waitFlag, int nextFlag)  {
        for (int i = 0; i < loopNumber; i++) {
            synchronized (this) {
                while (flag != waitFlag) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.print(str);
                this.flag = nextFlag;
                this.notifyAll();
            }
        }
    }
}