package com.gpdata.template.lambda.inter;

/**
 * Created by acer_liuyutong on 2017/6/19.
 */
public class Man implements Person ,A{
	@Override
	public void like() {
		System.out.println("i like her...");
	}

	private static void print(A a){
		a.foo();
	}
	public static void main(String[] args) {
		B b = () -> System.out.println("helloWorld..>>>A");
		A a = () -> System.out.println("helloWorld..>>>A");
		print(a);
//		print(b);

		Man man = new Man();
		man.bar();

	}

	@Override
	public void foo() {
		System.out.println("man foo");
	}

}