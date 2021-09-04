package com.ling.class08;

import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import lombok.extern.slf4j.Slf4j;
import org.omg.CORBA.DATA_CONVERSION;

import java.io.ObjectOutputStream;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author zhangling  2021/8/22 21:23
 */
public class TestReadWriteLock {
    public static void main(String[] args) {
        DataContainer dataContainer = new DataContainer();
        // 两个线程读不互斥
        new Thread(()->{
            dataContainer.read();
        },"t1").start();
        new Thread(()->{
            dataContainer.read();
        },"t2").start();
    }
}

@Slf4j(topic = "c.ReadWriteLock")
class DataContainer {
    private Object data;
    private ReentrantReadWriteLock rw = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.ReadLock r = rw.readLock();
    private ReentrantReadWriteLock.WriteLock w = rw.writeLock();

    public Object read() {
        log.debug("获取读锁...");
        r.lock();
        try {
            log.debug("读取...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return data;
        } finally {
            log.debug("释放读锁...");
            r.unlock();
        }
    }

    public void write() {
        log.debug("获取写锁...");
        w.lock();
        try {
            log.debug("写入...");
        } finally {
            log.debug("释放写锁...");
            w.unlock();
        }

    }
}
