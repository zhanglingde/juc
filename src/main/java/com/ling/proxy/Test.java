package com.ling.proxy;

/**
 * @author zhangling
 * @date 2022/4/6 8:39 下午
 */
public class Test {
	public static void main(String[] args) {
		// 获取代理对象
		Calculator proxy = CalculatorProxy.getProxy(new MyCalculator());
		proxy.add(1,1);
		System.out.println(proxy.getClass());
	}
}
