package com.ling.class08;

import lombok.extern.slf4j.Slf4j;
import sun.java2d.Disposer;

import java.sql.Time;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author zhangling  2021/8/22 15:58
 */
@Slf4j(topic = "c.Shutdown")
public class TestShutdown {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> result1 = pool.submit(() -> {
            log.debug("task 1 start...");
            Thread.sleep(1000);
            log.debug("task 1 finish...");
            return 1;
        });
        Future<Integer> result2 = pool.submit(() -> {
            log.debug("task 2 start...");
            Thread.sleep(1000);
            log.debug("task 2 finish...");
            return 1;
        });
        // 任务3 进队列
        Future<Integer> result3 = pool.submit(() -> {
            log.debug("task 3 start...");
            Thread.sleep(1000);
            log.debug("task 3 finish...");
            return 1;
        });
        log.debug("shutdown...");
        // pool.shutdown();        // 不阻塞
        List<Runnable> runnables = pool.shutdownNow();
        for (Runnable runnable : runnables) {
            log.debug("{}",runnable);
        }
        // log.debug("other... {}",runnables);


        // shutdown 之后的任务不能提交，报异常
        // Future<Integer> result4 = pool.submit(() -> {
        //     log.debug("task 3 start...");
        //     Thread.sleep(1000);
        //     log.debug("task 3 finish...");
        //     return 1;
        // });
    }
}
