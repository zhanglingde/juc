package com.ling.class08;

import lombok.extern.slf4j.Slf4j;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangling  2021/8/22 16:55
 */
@Slf4j(topic = "c.Timer")
public class TestTimer {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);
        log.debug("start...");
        // 参数2 初始时间间隔 参数3：时间间隔
        // pool.scheduleAtFixedRate(()->{
        //    log.debug("task 1");
        //
        //     // 任务执行时间过长，会影响时间间隔
        //     try {
        //         Thread.sleep(2000);
        //     } catch (InterruptedException e) {
        //         e.printStackTrace();
        //     }
        // },2,1, TimeUnit.SECONDS);

        pool.scheduleWithFixedDelay(()->{
            log.debug("task 1");

            // 任务执行时间过长，会影响时间间隔
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },2,2,TimeUnit.SECONDS);
    }

    private static void method2(ScheduledExecutorService pool) {
        // 延时 1s 执行任务
        pool.schedule(()->{
            log.debug("task 1");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },1, TimeUnit.SECONDS);

        pool.schedule(()->{
            log.debug("task 2");
        },1, TimeUnit.SECONDS);
    }

    private static void method1() {
        Timer timer = new Timer();
        TimerTask task1 = new TimerTask(){
            @Override
            public void run() {
                log.debug("task 1");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        TimerTask task2 = new TimerTask(){
            @Override
            public void run() {
                log.debug("task 2");
            }
        };
        log.debug("start...");
        timer.schedule(task1,1000);
        // 任务2等 任务1 执行完后才开始执行
        timer.schedule(task2,1000);
    }
}
