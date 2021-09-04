package com.ling.class06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * AtomicStampedReference 解决 ABA 问题
 */
@Slf4j(topic = "c.ABA")
public class Demo03_AtomicStampedReference {

    static AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        String prev = ref.getReference();
        int stamp = ref.getStamp();         // 获取版本号
        log.debug("version:{}", stamp);
        other();
        Thread.sleep(1000);
        log.debug("change A->C {}", ref.compareAndSet(prev, "C", stamp, stamp + 1));

    }

    public static void other() throws InterruptedException {
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("version:{}", stamp);
            log.debug("change A->B {}", ref.compareAndSet(ref.getReference(), "B", stamp, stamp + 1));
        }, "t1").start();
        Thread.sleep(500);
        new Thread(() -> {
            int stamp = ref.getStamp();
            log.debug("version:{}", stamp);
            log.debug("change B->A {}", ref.compareAndSet(ref.getReference(), "A", stamp, stamp + 1));
        }, "t2").start();

    }
}
