package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

import java.io.ObjectOutputStream;

/**
 * @author zhangling  2021/8/20 9:33
 */
@Slf4j(topic = "c.WaitNotify")
public class Demo05_wait_notify {

    private final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->{
            // wait-notify 必须配合 synchronized 先获得锁
            synchronized (lock) {
                log.debug("t1 start...");
                try {
                    lock.wait(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t1 end...");
            }
        },"t1").start();

        new Thread(()->{
            synchronized (lock) {
                log.debug("t2 start...");
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("t2 end...");
            }
        },"t2").start();

        Thread.sleep(1000);
        log.debug("main notify...");
        // synchronized (lock) {
            // lock.notify();          // 任意唤醒一个线程
            // lock.notifyAll();          // 唤醒所有 WaitSet 中 WAITING 的线程
        // }

    }
}
