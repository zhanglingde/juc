package com.ling.class07;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * 可变类不是线程安全的
 */
@Slf4j(topic = "c.Test1")
public class Test01 {
    public static void main(String[] args) {
        // test01();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                LocalDate date = dtf.parse("2021-08-01", LocalDate::from);
                log.debug("{}", date);
            }).start();
        }

    }

    private static void test01() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 多线程会出现异常
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    log.debug("{}", sdf.parse("2021-08-01"));
                } catch (ParseException e) {
                    log.error("{}", e);
                }
            }).start();
        }
    }
}
