package com.ling.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁与非公平锁
 */
public class Demo04 extends Thread {
    // 默认是非公平锁
    // 参数为true表示为公平锁，请对比输出结果
    private static ReentrantLock lock = new ReentrantLock(true);

    // Lock lock = new ReentrantLock(false);
    public void run() {
        for (int i = 0; i < 100; i++) {
            lock.lock();
            try {
                System.out.println(i + Thread.currentThread().getName() + "获得锁");
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Demo04 rl = new Demo04();
        Thread t1 = new Thread(rl);
        Thread t2 = new Thread(rl);
        t1.start();
        t2.start();
    }
}
