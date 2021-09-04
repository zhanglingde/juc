package com.ling.test;

/**
 * 打印 ABCABCABC
 */
public class Test3 {
    static final Object lock = new Object();
    static int flag = 1;   // 1 打印A,2 打印B,3 打印C

    public static void main(String[] args) {
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (flag != 1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("A");
                    flag = 2;
                    lock.notifyAll();
                }
            }
        }, "t1").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (flag != 2) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("B");
                    flag = 3;
                    lock.notifyAll();
                }
            }

        }, "t2").start();

        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                synchronized (lock) {
                    while (flag != 3) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("C");
                    flag = 1;
                    lock.notifyAll();

                }
            }
        }, "t3").start();
    }
}
