package com.ling.class06;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicReference;

/**
 * ABA 问题
 */
@Slf4j(topic = "c.ABA")
public class Demo03_ABA {

    static AtomicReference<String> ref = new AtomicReference<>("A");

    public static void main(String[] args) throws InterruptedException {
        log.debug("main start...");
        String prev = ref.get();
        other();
        Thread.sleep(1000);
        log.debug("change A->C {}", ref.compareAndSet(prev, "C"));

    }

    public static void other() throws InterruptedException {
        new Thread(() -> {
            log.debug("change A->B {}", ref.compareAndSet(ref.get(), "B"));
        }, "t1").start();
        Thread.sleep(500);
        new Thread(() -> {
            log.debug("change B->A {}", ref.compareAndSet(ref.get(), "A"));
        }, "t2").start();

    }
}
