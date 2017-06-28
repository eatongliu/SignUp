package com.gpdata.template.lambda.inter;

/**
 * Created by acer_liuyutong on 2017/6/19.
 */
public interface B {
	void foo();

	default void bar(){
		System.out.println("B --- bar");
	}
}