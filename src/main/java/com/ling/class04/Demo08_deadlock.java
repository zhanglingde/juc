package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

/**
 * 死锁问题
 */
@Slf4j(topic = "c.DeadLock")
public class Demo08_deadlock {
    public static void main(String[] args) {
        Object A = new Object();
        Object B = new Object();

        Thread t1 = new Thread(()->{
            synchronized (A) {
                log.debug("lock A");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    log.debug("lock B");
                    log.debug("操作...");
                }
            }
        },"t1");

        Thread t2 = new Thread(()->{
            synchronized (B) {
                log.debug("lock B");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (A) {
                    log.debug("lock A");
                    log.debug("操作...");
                }
            }
        },"t2");

        t1.start();
        t2.start();
    }
}
