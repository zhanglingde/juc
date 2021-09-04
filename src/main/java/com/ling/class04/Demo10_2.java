package com.ling.class04;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangling  2021/8/20 20:11
 */
public class Demo10_2 {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            LockSupport.park();
            System.out.println("A");
        }, "t1");

        Thread t2 = new Thread(() -> {
            System.out.println("B");
            LockSupport.unpark(t1);
        }, "t2");
        t1.start();
        t2.start();

    }
}
