package com.ling.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 可打断
 */
@Slf4j(topic = "c.ReentrantLock")
public class Demo02 {
    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            try {
                // 如果没有竞争那么此方法就会获取 lock 对象锁
                // 如果有竞争就进入阻塞队列，可以被其他线程用 interrupt 方法打断
                log.debug("尝试获得锁...");
                lock.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.debug("没有获得锁，返回");
                return;
            }
            try{
                log.debug("t1 获得锁...");
            }finally {
                lock.unlock();
            }
        },"t1");

        t1.start();

        // main 线程与 t1 线程竞争获得锁，有竞争 t1 就进入阻塞队列
        log.debug("main 获得锁");
        lock.lock();

        Thread.sleep(1000);
        // 打断 t1，t1 退出
        log.debug("打断 t1");
        t1.interrupt();

        log.debug("main 释放锁");
    }
}
