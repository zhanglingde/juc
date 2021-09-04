package com.ling.class08;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author zhangling  2021/8/22 14:56
 */
@Slf4j(topic = "c.Submit")
public class TestSubmit {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        String result = pool.invokeAny(Arrays.asList(
                // 任务一
                () -> {
                    log.debug("task 1 begin...");
                    Thread.sleep(1000);
                    log.debug("task 1 end...");
                    return "任务1";
                },
                // 任务2
                () -> {
                    log.debug("task 2 begin...");
                    Thread.sleep(500);
                    log.debug("task 2 end...");
                    return "任务2";
                },
                // 任务3
                () -> {
                    log.debug("task 3 begin...");
                    Thread.sleep(2000);
                    log.debug("task 3 end...");
                    return "任务3";
                }
        ));
        log.debug("结果：{}",result);

    }

    private static void method2(ExecutorService pool) throws InterruptedException, ExecutionException {
        List<Future<String>> futures = pool.invokeAll(Arrays.asList(
                // 任务一
                () -> {
                    log.debug("task 1 begin...");
                    Thread.sleep(1000);
                    return "任务1";
                },
                // 任务2
                () -> {
                    log.debug("task 2 begin...");
                    Thread.sleep(500);
                    return "任务2";
                },
                // 任务3
                () -> {
                    log.debug("task 3 begin...");
                    Thread.sleep(2000);
                    return "任务3";
                }
        ));
        for (Future<String> future : futures) {
            log.debug("结果：{}",future.get());
        }
    }

    private static void method1(ExecutorService pool) throws InterruptedException, ExecutionException {
        Future<String> future = pool.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                log.debug("running...");
                Thread.sleep(1000);
                return "ok";
            }
        });
        log.debug("结果：{}",future.get());
    }
}
