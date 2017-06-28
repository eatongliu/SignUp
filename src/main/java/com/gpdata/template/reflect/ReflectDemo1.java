package com.gpdata.template.reflect;

/**
 * Created by acer_liuyutong on 2017/6/13.
 */
public class ReflectDemo1 implements Demo{
	public ReflectDemo1() {}

	public ReflectDemo1(String name, Integer age){}

	public ReflectDemo1(String name, Integer age, Integer type){}

	@Override
	public void getDemo() {
		System.out.println("getDemo()...");
	}

	public void saveDemo(){
		System.out.println("saveDemo()...");
	}
}
