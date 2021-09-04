package com.ling.class08;

import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

/**
 * @author zhangling  2021/8/22 23:32
 */
public class TestStampeedLock {
    public static void main(String[] args) throws Exception {
        DataContainerStamped dataContainer = new DataContainerStamped(1);
        new Thread(() -> {
            try {
                dataContainer.read(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();
        Thread.sleep(500);
        new Thread(() -> {
            dataContainer.write(10);
        }, "t2").start();

    }
}

@Slf4j(topic = "c.StampedLock")
class DataContainerStamped {
    private int data;
    private final StampedLock lock = new StampedLock();

    public DataContainerStamped(int data) {
        this.data = data;
    }

    /**
     * @param readTime 读取耗费的时间
     * @return
     */
    public int read(int readTime) throws InterruptedException {
        long stamp = lock.tryOptimisticRead();
        log.debug("optimistic read locking... {}", stamp);
        Thread.sleep(readTime);
        if (lock.validate(stamp)) {
            log.debug("read finish...");
            return data;
        }
        // 有写线程修改了 stamp，升级为读锁
        log.debug("updating to read lock...{}", stamp);
        try {
            stamp = lock.readLock();
            log.debug("read lock {}", stamp);
            Thread.sleep(readTime);
            log.debug("read finish {}", stamp);
            return data;
        } finally {
            log.debug("read unlock {}", stamp);
            lock.unlockRead(stamp);
        }
    }

    // 写操作
    public void write(int newDate) {
        long stamp = lock.writeLock();
        log.debug("write lock {}", stamp);
        try {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.data = newDate;
        } finally {
            log.debug("write unlock {}", stamp);
            lock.unlockWrite(stamp);
        }
    }
}
