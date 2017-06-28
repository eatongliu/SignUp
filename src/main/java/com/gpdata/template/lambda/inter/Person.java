package com.gpdata.template.lambda.inter;

/**
 * Created by acer_liuyutong on 2017/6/19.
 */
@FunctionalInterface
public interface Person {

	default void say(){
		System.out.println("hello...");
	}

	static void hate(){
		System.out.println("i hate it...");
	}

	void like();
}