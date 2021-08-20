package com.ling.class04;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * 保护性暂停
 */
@Slf4j(topic = "c.GuardedObject")
public class GuardedObject {

    private Object response;
    // 同步等待获取结果
    public Object get() {
        synchronized (this) {
            // while 防止虚假唤醒
            while (response == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return response;
        }
    }

    // 产生结果
    public void complete(Object response) {
        synchronized (this) {
            this.response = response;
            // 产生结果后唤醒等待结果的线程
            this.notifyAll();
        }
    }

    // 线程1 等待 线程2 的现在结果
    public static void main(String[] args) {
        GuardedObject guardedObject = new GuardedObject();
        new Thread(()->{
            // 阻塞等待
            log.debug("t1 waiting...");
            List<String> list = (List<String>) guardedObject.get();
            log.debug("结果是：{}",list.size());


        },"t1").start();

        new Thread(()->{
            try {
                log.debug("t2 download...");
                List<String> list = Downloader.download();
                guardedObject.complete(list);
                log.debug("t2 end...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        },"t2").start();
    }
}
