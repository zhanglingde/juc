package com.ling.class03;

import lombok.extern.slf4j.Slf4j;

/**
 * Interrupt 打断阻塞中的线程
 */
@Slf4j(topic = "c.interrupt")
public class Demo04_interrupt_sleep {

    public static void main(String[] args) throws InterruptedException {
        test2();
    }


    // 1) 打断阻塞中的线程
    public static void test1() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("sleep...");
            try {
                Thread.sleep(5000);  // wait，join
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.start();
        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
        log.debug("打断标记：{}", t1.isInterrupted());
    }

    // 主线程打断正在运行的线程
    public static void test2() throws InterruptedException {
        Thread t1 = new Thread(() -> {
            while (true) {
                boolean interrupted = Thread.currentThread().isInterrupted();
                if (interrupted) {
                    log.debug("线程被打断，退出循环");
                    break;
                }
            }
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt");
        t1.interrupt();
    }
}
