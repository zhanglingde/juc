package com.ling.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 配合 Thread 创建线程
 */
@Slf4j(topic = "c.CallableTest")
public class CallableTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 创建任务对象
        FutureTask<Integer> task3 = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.debug("hello");
                Thread.sleep(2000);
                return 100;
            }
        });
        // 创建线程
        Thread t3 = new Thread(task3, "t3");
        t3.start();
        // get 方法阻塞等待，知道 task 任务执行完毕返回结果
        Integer result = task3.get();
        log.debug("结果是：{}", result);
    }
}
