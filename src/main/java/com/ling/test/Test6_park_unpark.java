package com.ling.test;

import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangling  2021/8/20 21:03
 */
public class Test6_park_unpark {
    static Thread t1;
    static Thread t2;
    static Thread t3;

    public static void main(String[] args) {
        ParkUnpark pu = new ParkUnpark(5);
        t1 = new Thread(() -> {
            pu.print("A", t2);
        }, "t1");
        t2 = new Thread(() -> {
            pu.print("B", t3);
        }, "t2");
        t3 = new Thread(() -> {
            pu.print("C", t1);
        }, "t3");
        t1.start();
        t2.start();
        t3.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);

    }
}

class ParkUnpark {
    private int loopNumber;

    public ParkUnpark(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public void print(String str, Thread next) {
        for (int i = 0; i < loopNumber; i++) {
            LockSupport.park();
            System.out.print(str);
            LockSupport.unpark(next);
        }
    }
}
