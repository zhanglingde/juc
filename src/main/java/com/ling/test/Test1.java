package com.ling.test;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

// logback 设置了 c 开头的才会打印日志到控制台
@Slf4j(topic = "c.Test1")
public class Test1 {
    volatile static List<Integer> list = new ArrayList<>();

    static Thread t1,t2;
    public static void main(String[] args) throws IOException, InterruptedException {
        final Object lock = new Object();
        CountDownLatch latch = new CountDownLatch(1);
        t2 = new Thread(() -> {
            log.debug("t2 start...");
            LockSupport.park();
            log.debug("t2 end...");
            LockSupport.unpark(t1);
        }, "t2");
        t2.start();

        Thread.sleep(1000);

        t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                list.add(i);
                log.debug("add {}", i);
                if (list.size() == 5) {
                    LockSupport.unpark(t2);
                    LockSupport.park();
                }
                // try {
                //     Thread.sleep(1000);
                // } catch (InterruptedException e) {
                //     e.printStackTrace();
                // }
            }
        }, "t1");
        t1.start();


        System.in.read();


    }
}
