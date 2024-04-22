package com.ling.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

/**
 * @author zhangling  2021/8/18 17:33
 */
@Slf4j(topic = "c.Test2")
public class Test2 {
    public static void main(String[] args) {
        MyQueue<String> queue = new MyQueue<>(10);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) {
                    log.debug("消费者消费 {}", queue.get());
                }
            }, "consumer:" + i).start();
        }

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) {
                    queue.put(Thread.currentThread().getName() + " " + j);
                }
            }, "producer:" + i).start();
        }
    }
}

@Slf4j(topic = "c.Test2")
class MyQueue<T> {
    private LinkedList<T> queue;
    private int capacity;

    public MyQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
    }

    // 队列尾部添加元素
    public void put(T t) {
        synchronized (queue) {
            while (queue.size() >= capacity) {
                log.debug("queue is full");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            queue.addLast(t);
            queue.notifyAll();
        }
    }

    public T get() {
        synchronized (queue) {
            while (queue.isEmpty()) {
                log.debug("queue is empty...");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            T t = queue.removeFirst();
            queue.notifyAll();
            return t;
        }

    }


}
