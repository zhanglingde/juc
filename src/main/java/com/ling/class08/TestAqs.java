package com.ling.class08;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author zhangling  2021/8/22 17:44
 */
@Slf4j(topic = "c.TestAQS")
public class TestAqs {
    public static void main(String[] args) {
        MyLock lock = new MyLock();
        new Thread(()->{
            lock.lock();
            try{
                log.debug("locking...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        },"t1").start();

        new Thread(()->{
            lock.lock();
            try{
                log.debug("locking...");
            }finally {
                log.debug("unlocking...");
                lock.unlock();
            }
        },"t2").start();

        // log.debug("main end...");
    }
}

// 同步器类（读占锁/不可重入锁）
class MySync extends AbstractQueuedSynchronizer {

    @Override
    protected boolean tryAcquire(int arg) {
        if (compareAndSetState(0, 1)) {
            // 加锁成功,并设置 owner 为当前线程
            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    @Override
    protected boolean tryRelease(int arg) {
        // 只有一个线程，不用 cas
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    // 是否持有独占锁
    @Override
    protected boolean isHeldExclusively() {
        return getState() == 1;
    }

    public Condition newCondition() {
        return new ConditionObject();
    }
}

// 自定义锁（不可重入锁）
class MyLock implements Lock {

    private MySync sync = new MySync();

    // 加锁（不成功进入等待队列）
    @Override
    public void lock() {
        sync.acquire(1);
    }

    // 加锁（可打断）
    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    // 尝试一次加锁
    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
