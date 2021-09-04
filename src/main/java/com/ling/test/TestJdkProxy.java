package com.ling.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * jdk 动态代理
 */
public class TestJdkProxy {
    public static void main(String[] args) {
        HelloImpl impl = new HelloImpl();
        // 通过 jdk动态代理 获取 Hello 的代理对象
        Hello hello = (Hello) Proxy.newProxyInstance(
                Hello.class.getClassLoader(),    // 1. 类加载器
                new Class[]{Hello.class},        // 2. 代理需要实现的接口，可以有多个
                new JdkProxy(impl));             // 3. 方法调用的实际处理者
        String result = hello.sayHello("hello");
        System.out.println("result = " + result);
    }
}

// 接口
interface Hello {
    String sayHello(String str);
}

// 实现业务方法
class HelloImpl implements Hello {

    @Override
    public String sayHello(String str) {
        System.out.println("业务代码：" + str);
        return "返回值";
    }
}

class JdkProxy implements InvocationHandler {

    // 聚合一个被代理类
    private Hello hello;

    public JdkProxy(Hello hello) {
        this.hello = hello;
    }

    /**
     * @param proxy  代理对象本身，JdkProxy 的对象，有可能用到
     * @param method 业务方法
     * @param args   调用的方法的参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("记录日志：before... ");
        Object result = method.invoke(hello, args);
        return result;
    }
}
