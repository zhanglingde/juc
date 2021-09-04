package com.ling.class06;

import java.awt.font.TextHitInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 测试 LongAddr,AtomicLong 累加操作效率
 */
public class Demo06_LongAddr_AtomicLong {

    public static void main(String[] args) {
        // AtomicLong
        for (int i = 0; i < 5; i++) {
            test(
                    () -> new AtomicLong(0),
                    (adder) -> adder.getAndIncrement()         // adder 对象执行累加操作
            );
        }

        // LongAdder
        for (int i = 0; i < 5; i++) {
            test(
                    () -> new LongAdder(),
                    adder -> adder.increment()
            );
        }

    }

    /**
     * 测试不同对象的累加耗费时间操作，对比性能
     *
     * @param adderSupplier () -> 结果 提供累加器对象
     * @param action        (参数)->void 执行累加操作
     * @param <T>
     */
    private static <T> void test(Supplier<T> adderSupplier,
                                 Consumer<T> action) {
        T adder = adderSupplier.get();
        List<Thread> threads = new ArrayList<>();
        // 4个线程，每个线程累加 50 万
        for (int i = 0; i < 4; i++) {
            threads.add(new Thread(() -> {
                for (int j = 0; j < 5000000; j++) {
                    action.accept(adder);
                }
            }));
        }
        long start = System.nanoTime();
        threads.forEach(Thread::start);
        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        long end = System.nanoTime();
        // 耗费时间，ns
        System.out.println(adder + " cost:" + (end - start) / 1000_1000);

    }

}
