package com.ling.class04;

import com.ling.test.Test1;
import lombok.extern.slf4j.Slf4j;

/**
 * 控制顺序
 */
@Slf4j(topic = "c.Demo10")
public class Demo10 {
    static final Object lock = new Object();
    // t1 是否运行过
    static boolean t1runed = false;

    public static void main(String[] args) {

        new Thread(()->{
            synchronized (lock) {
                System.out.println("A");
                t1runed = true;
                lock.notifyAll();
            }
        },"t1").start();

        new Thread(()->{
            synchronized (lock) {
                while (!t1runed) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("B");
            }
        },"t2").start();

    }
}
