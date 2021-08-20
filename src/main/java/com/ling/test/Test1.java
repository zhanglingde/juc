package com.ling.test;


import lombok.extern.slf4j.Slf4j;

// logback 设置了 c 开头的才会打印日志到控制台
@Slf4j(topic = "c.Test1")
public class Test1 {
    public static void main(String[] args) {
        // 匿名内部类创建线程
        Thread t = new Thread(){
            @Override
            public void run() {
                log.debug("running...");
            }
        };

        t.setName("t");
        t.start();
        log.debug("running...");
    }
}
