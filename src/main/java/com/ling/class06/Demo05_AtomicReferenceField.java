package com.ling.class06;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 字段更新器，更新对象中的字段，保证线程安全
 */
public class Demo05_AtomicReferenceField {
    public static void main(String[] args) {
        Student student = new Student();
        // 为 Student 类的 name 属性创建更新器
        AtomicReferenceFieldUpdater updater =
                AtomicReferenceFieldUpdater.newUpdater(Student.class, String.class, "name");

        // 修改 stuend 对象的 name 属性
        System.out.println(updater.compareAndSet(student, null, "张三"));
        System.out.println(student);
    }
}
class Student{
    // CAS 配合 volatile 使用
    volatile String name;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
