package com.ling.class06;

import sun.misc.Unsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangling  2021/8/21 20:49
 */
public class TestUnsafe_Account {
    public static void main(String[] args) {
        MyAtomicIntegerAccount.test(new MyAtomicIntegerAccount(10000));
    }
}

class MyAtomicIntegerAccount implements Account {
    private MyAtomicInteger balance;

    public MyAtomicIntegerAccount(int balance) {
        this.balance = new MyAtomicInteger(balance);
    }

    @Override
    public Integer getBalance() {
        return balance.getValue();
    }

    @Override
    public void withDraw(Integer amount) {
        balance.decrement(amount);
    }

    public static void test(MyAtomicIntegerAccount account){
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(()->{
                account.withDraw(10);
            }));
        }
        threads.forEach(Thread::start);
        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(account.getBalance());
    }


}

class MyAtomicInteger {
    private volatile int value;
    private static final long valueOffset;
    static final Unsafe UNSAFE;

    public MyAtomicInteger(int value) {
        this.value = value;
    }

    static {
        UNSAFE = UnsafeAccessor.getUnsafe();
        try {
            valueOffset = UNSAFE.objectFieldOffset(MyAtomicInteger.class.getDeclaredField("value"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public int getValue() {
        return value;
    }

    // 扣减
    public void decrement(int amount) {
        while (true) {
            int prev = this.value;
            int next = prev - amount;
            if(UNSAFE.compareAndSwapInt(this, valueOffset, prev, next)){
                break;
            }
        }

    }
}
