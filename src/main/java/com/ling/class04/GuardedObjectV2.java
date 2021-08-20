package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 保护性暂停
 */
@Slf4j(topic = "c.GuardedObject")
public class GuardedObjectV2 {

    private Object response;
    private final Object lock = new Object();

    // 同步等待获取结果
    public Object get(long timeout) {
        synchronized (lock) {
            // 1) 记录最初时间
            long begin = System.currentTimeMillis();
            // 2) 已经经历的时间
            long timePassed = 0;
            // while 防止虚假唤醒
            while (response == null) {
                // 4) 假设 timeout 是 1000,结果在 400 时唤醒了，那么还有 600 需要等
                long waitTime = timeout - timePassed;
                log.debug("waitTime:{}", waitTime);
                if (waitTime <= 0) {
                    log.debug("break...");
                    break;
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // 3) 如果提前被唤醒，这时已经经历时间假设为 400
                timePassed = System.currentTimeMillis() - begin;
                log.debug("timePassed:{},Object is null {}", timePassed, response == null);
            }
            return response;
        }
    }

    // 产生结果
    public void complete(Object response) {
        synchronized (lock) {
            this.response = response;
            // 产生结果后唤醒等待结果的线程
            log.debug("notify...");
            this.notifyAll();
        }
    }

    public static void main(String[] args) {
        GuardedObjectV2 v2 = new GuardedObjectV2();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                v2.complete(null);
                Thread.sleep(1000);
                v2.complete(Arrays.asList("a", "b", "c"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        Object response = v2.get(2500);
        if (response != null) {
            log.debug("get response: [{}] lines", ((List<String>) response).size());
        } else {
            log.debug("can't get response");
        }

    }
}