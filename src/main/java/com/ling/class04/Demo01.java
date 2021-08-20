package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangling  2021/8/19 14:43
 */

@Slf4j(topic = "c.Demo01")
public class Demo01 {
    private static int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                count++;
            }
        },"t1");

        Thread t2 = new Thread(()->{
            for (int i = 0; i < 5000; i++) {
                count--;
            }
        },"t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        log.debug("{}",count);
    }
}
