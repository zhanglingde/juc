package com.ling.class03;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;

/**
 * interrupt 打断 park 阻塞线程
 */
@Slf4j(topic = "c.Park")
public class Demo04_interrupt_park {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            log.debug("park...");
            LockSupport.park();     // LockSupport.park() 方法会阻塞线程
            log.debug("unpark...");
            // log.debug("打断标记：{}", Thread.interrupted());     // 返回当前打断标记后清除打断标记，变为 false
            log.debug("打断标记：{}", Thread.currentThread().isInterrupted());  //不会清除打断标记，为true

            LockSupport.park();     // 打断标记为 true 时，park方法无法阻塞线程
            log.debug("unpark...");
        }, "t1");
        t1.start();

        Thread.sleep(1000);
        log.debug("interrupt...");
        t1.interrupt();
    }
}
