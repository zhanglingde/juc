package com.ling.class03;

import lombok.extern.slf4j.Slf4j;

/**
 * 守护线程
 */
@Slf4j(topic = "c.Deamon")
public class Demo05_Daemon {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    break;
                }
            }
        },"t1");
        t1.setDaemon(true);  // 设置为守护线程
        t1.start();

        Thread.sleep(1000);
        log.debug("end...");
    }
}
