package com.gpdata.template.lambda.inter;

import org.junit.Test;

/**
 * Created by acer_liuyutong on 2017/6/19.
 */
public class InterTest {
	@Test
	public void test1(){
		Man man = new Man();
		man.like();

		Person person = () -> System.out.println("i like the girl");
		person.like();
		person.say();
		Person.hate();
	}

}