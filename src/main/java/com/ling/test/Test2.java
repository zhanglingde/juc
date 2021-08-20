package com.ling.test;

import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangling  2021/8/18 17:33
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                log.debug("run...");
            }
        },"t").start();

        log.debug("run");
    }
}
