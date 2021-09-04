package com.ling.class06;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * 获取 Unsafe 对象
 */
public class UnsafeAccessor {
    static Unsafe unsafe;

    static {
        try {
            // 获取 Unsafe 对象实例
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            // 允许访问私有
            theUnsafe.setAccessible(true);
            unsafe = (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new Error(e);
        }
    }

    static Unsafe getUnsafe(){
        return unsafe;
    }

}
