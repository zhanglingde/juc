package com.ling.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 条件变量
 */
@Slf4j(topic = "c.ReentrantLock")
public class Demo05 {
    static ReentrantLock lock = new ReentrantLock();
    // 创建一个新的添加变量（休息室）
    static Condition waitCigaretteQueue = lock.newCondition();
    static Condition waitBreakfastQueue = lock.newCondition();
    static volatile boolean hasCigarette = false;
    static volatile boolean hasBreakfast = false;

    public static void main(String[] args) throws InterruptedException {
        // t1 等到烟才开始工作
        new Thread(()->{
            try {
                lock.lock();
                while (!hasCigarette){
                    try {
                        waitCigaretteQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t1 等到了它的烟,开始工作...");
            }finally {
                lock.unlock();
            }
        },"t1").start();
        // t2 等到外卖才开始工作
        new Thread(()->{
            try{
                lock.lock();
                while (!hasBreakfast){
                    try {
                        waitBreakfastQueue.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("t2 等到了它的外卖,开始工作...");
            }finally {
                lock.unlock();
            }
        },"t2").start();

        log.debug("start...");
        Thread.sleep(1000);
        sendBreakfast();
        Thread.sleep(1000);
        sendCigarette();

    }

    private static void sendCigarette(){
        lock.lock();
        try{
            log.debug("烟送到了...");
            hasCigarette = true;
            waitCigaretteQueue.signal();
        }finally {
            lock.unlock();
        }
    }

    private static void sendBreakfast(){
        lock.lock();
        try{
            log.debug("外卖送到了...");
            hasBreakfast = true;
            waitBreakfastQueue.signal();
        }finally {
            lock.unlock();
        }
    }
}
