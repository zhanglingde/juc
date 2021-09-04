package com.ling.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangling  2021/8/20 20:51
 */
public class Test5 {
    public static void main(String[] args) throws InterruptedException {
        AwaitSignal awaitSignal = new AwaitSignal(5);
        Condition a = awaitSignal.newCondition();
        Condition b = awaitSignal.newCondition();
        Condition c = awaitSignal.newCondition();
        new Thread(()->{
            awaitSignal.print("a",a,b);
        },"t1").start();
        new Thread(()->{
            awaitSignal.print("b",b,c);
        },"t1").start();
        new Thread(()->{
            awaitSignal.print("c",c,a);
        },"t1").start();

        Thread.sleep(1000);
        awaitSignal.lock();
        try {
            System.out.println("开始...");
            a.signal();
        }finally {
            awaitSignal.unlock();
        }
    }
}

class AwaitSignal extends ReentrantLock {
    // 循环次数
    private int loopNumber;

    public AwaitSignal( int loopNumber) {
        this.loopNumber = loopNumber;
    }

    //          参数1 打印内容 ， 参数2 进入哪一间休息室     参数3 进入下一间休息室
    public void print(String str, Condition current, Condition next) {
        for (int i = 0; i < loopNumber; i++) {
            this.lock();
            try {
                current.await();
                System.out.print(str);
                next.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                this.unlock();
            }
        }
    }

}
