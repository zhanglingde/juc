package com.ling.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author zhangling  2021/9/2 20:45
 */
@Slf4j(topic = "c.Demo01")
public class Demo01 {
    static AtomicInteger i = new AtomicInteger(1);
    static Thread t1;
    static Thread t2;

    public static void main(String[] args) {
        Print p = new Print(50);

        t1 = new Thread(() -> {
            p.print(i, t2);
        }, "t1");

        t2 = new Thread(() -> {
            p.print(i, t1);
        }, "t2");
        t1.start();
        t2.start();
        LockSupport.unpark(t1);
    }

}

@Slf4j(topic = "c.Demo01")
class Print {
    int loop;

    public Print(int loop) {
        this.loop = loop;
    }

    public void print(AtomicInteger num, Thread thread) {
        for (int j = 0; j < loop; j++) {
            LockSupport.park();
            log.debug("{}", num.getAndIncrement());
            LockSupport.unpark(thread);
        }
    }
}
