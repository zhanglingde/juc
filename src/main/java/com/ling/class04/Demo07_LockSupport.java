package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * LockSupport.park()可能让线程阻塞，不需要加锁，而wait()需要加锁；
 * LockSupport.unpark()可以唤醒某个指定的线程，notify()不能唤醒等待队列中的指定线程
 */
@Slf4j(topic = "c.LockSupport")
public class Demo07_LockSupport {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            log.debug("t1 start...");
            log.debug("t1 wait...");
            LockSupport.park();                 // 当前线程阻塞，等待其他线程调用 unpark 唤醒
            log.debug("t1 end...");

        }, "t1");
        t1.start();
        // 唤醒某个线程t，unpark可以先于park调用，主线程先调用unpark,线程t调用park不会阻塞
        LockSupport.unpark(t1);
        // try {
        //     Thread.sleep(2000);
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        // log.debug("main unpark...");
        // LockSupport.unpark(t1);
    }
}
