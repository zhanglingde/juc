package com.ling.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 锁超时tryLock
 */
@Slf4j(topic = "c.ReentrantLock")
public class Demo03 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            log.debug("t1 尝试获得锁");
            try {
                if(!lock.tryLock(2, TimeUnit.SECONDS)){
                    log.debug("t1 未获得锁...");
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                // tryLock 被打断了
                log.debug("t1 未获得锁...");
                return;
            }
            try{
                log.debug("t1 获得锁...");
            }finally {
                log.debug("t1 释放锁...");
                lock.unlock();
            }
        },"t1");

        t1.start();
        log.debug("main 获得锁...");
        lock.lock();
        Thread.sleep(1000);
        log.debug("main 释放锁...");
        lock.unlock();

    }
}
