package com.ling.class06;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author zhangling  2021/8/21 14:29
 */
public class Demo02_BigDecimalAccount {
    public static void main(String[] args) {
        DecimalAccount decimalAccount = new DecimalAccount(new BigDecimal("10000"));
        DecimalAccount.test(decimalAccount);
    }
}

class DecimalAccount{
    private AtomicReference<BigDecimal> balance;

    public DecimalAccount(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    public BigDecimal getBalance(){
        return balance.get();
    }

    public void withdraw(BigDecimal amount) {
        while (true) {
            BigDecimal prev = balance.get();
            BigDecimal next = prev.subtract(amount);
            if (balance.compareAndSet(prev, next)) {
                break;
            }
        }
    }

    // 测试，方法内启动 1000个线程，没个线程 10，最后应为 0
    public static void test(DecimalAccount account) {
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            threads.add(new Thread(()->{
                account.withdraw(BigDecimal.TEN);
            }));
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(account.getBalance());
    }
}
