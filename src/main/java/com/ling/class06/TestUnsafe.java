package com.ling.class06;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author zhangling  2021/8/21 20:34
 */
public class TestUnsafe {
    public static void main(String[] args) throws Exception {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe =(Unsafe) theUnsafe.get(null);

        System.out.println(unsafe);

        // 1. 获取域的偏移地址
        long idOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("id"));
        long nameOffset = unsafe.objectFieldOffset(User.class.getDeclaredField("name"));

        User user = new User();
        System.out.println(user);
        // 2.执行 CAS 操作
        // 参数一：对象  参数2：域的偏移量   参数3：原值    参数4：修改后的值
        unsafe.compareAndSwapInt(user, idOffset, 0, 1);
        unsafe.compareAndSwapObject(user, nameOffset, null, "张三");

        System.out.println(user);

    }
}

@Data
class User {
    volatile int id;
    volatile String name;
}
