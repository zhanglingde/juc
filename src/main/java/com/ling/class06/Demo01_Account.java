package com.ling.class06;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 账户余额扣减
 */
public class Demo01_Account {
    public static void main(String[] args) {
        AccountCas account = new AccountCas(10000);
        account.withDraw(10);
    }
}

class AccountCas {

    private AtomicInteger balance;

    public AccountCas(Integer balance) {
        this.balance = new AtomicInteger(balance);
    }

    // 获取余额
    public Integer getBalance() {
        return balance.get();
    }

    // 取款金额
    public void withDraw(Integer amount) {
        while (true) {
            // 获取余额的最新值
            int prev = balance.get();
            // 取款后的余额
            Integer next = prev - amount;

            if (balance.compareAndSet(prev, next)) {
                // 修改成功后退出
                break;
            }
            // 修改未成功循环
        }
    }
}
