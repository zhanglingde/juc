package com.ling.class08;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhangling  2021/8/23 9:42
 */
@Slf4j(topic = "c.CyclicBarrier")
public class TestCyclicBarrier {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        // 参数二 Runnable，当满足计数个数的线程执行完后，Runnable 开始执行
        CyclicBarrier barrier = new CyclicBarrier(2,()->{
            log.debug("task 1,task 2 end...");
        });

        for (int i = 0; i < 3; i++) {
            pool.submit(() -> {
                log.debug("task 1 start...");
                try {
                    Thread.sleep(1000);
                    barrier.await();
                    // log.debug("task 1 end...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });

            pool.submit(() -> {
                log.debug("task 2 start...");
                try {
                    Thread.sleep(2000);
                    barrier.await();
                    // log.debug("task 2 end...");
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();
    }
}
