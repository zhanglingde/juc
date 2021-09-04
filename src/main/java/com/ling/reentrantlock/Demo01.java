package com.ling.reentrantlock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * 可以多次调用 lock 方法，说明可重入
 */
@Slf4j(topic = "c.ReentrantLock")
public class Demo01 {

    private static ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();
        try{
            log.debug("enter main...");
            m1();
        }finally {
            lock.unlock();
        }
    }

    public static void m1(){
        lock.lock(); // 再次调用 lock
        try{
            log.debug("enter m1...");
            m2();
        }finally {
            lock.unlock();
        }
    }

    public static void m2(){
        lock.lock();
        try{
            log.debug("enter m2...");
        }finally {
            lock.unlock();
        }
    }
}
