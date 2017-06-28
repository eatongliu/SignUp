package com.gpdata.template.reflect;

import com.gpdata.template.user.entity.User;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by acer_liuyutong on 2017/6/13.
 */
public class ReflectTest {
	@Test
	public void test1(){
		System.out.println(ReflectDemo1.class);
		ReflectDemo1 demo1 = new ReflectDemo1();
		System.out.println(demo1);
		System.out.println(demo1.getClass());
		System.out.println(demo1.getClass());
		System.out.println(demo1.getClass().getName());
	}

	@Test
	public void test2(){
		User user = new User();
		Class demo1 = null;
		Class demo2 = null;
		Class demo3 = null;

		try {
			demo1 = Class.forName("com.gpdata.template.reflect.ReflectDemo1");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		demo2 = new User().getClass();
		demo3 = User.class;

		System.out.println(demo1);
		System.out.println(demo1.getName());
		System.out.println(demo2);
		System.out.println(demo2.getName());
		System.out.println(demo3);
		System.out.println(demo3.getName());
	}

	@Test
	public void test3(){
		Class<ReflectDemo1> demo = ReflectDemo1.class;
		Class<?>[] interfaces = demo.getInterfaces();
		Arrays.stream(interfaces).forEach(System.out::println);
		Class<? super ReflectDemo1> superclass = demo.getSuperclass();
		System.out.println(superclass);
	}

	@Test
	public void test4(){
		Class<ReflectDemo1> user = ReflectDemo1.class;
		Constructor<?>[] constructors = user.getConstructors();
		Arrays.stream(constructors).forEach(constructor -> {
			StringBuilder builder = new StringBuilder();
			int modifiers = constructor.getModifiers();
			builder.append("modifiers:").append(modifiers).append(" ");
			builder.append(Modifier.toString(modifiers)).append(" ");
			builder.append(constructor.getName()).append("(");

			Class<?>[] paramTypes = constructor.getParameterTypes();

			for (int i = 0; i < paramTypes.length; i++) {
				builder.append(paramTypes[i].getName()).append(" arg").append(i);
				if (i < paramTypes.length - 1) {
					builder.append(", ");
				}
			}
//			Arrays.stream(paramTypes).forEach(paramType ->
//					builder.append(paramType.getName()).append(" arg").append(", "));

			builder.append(")");
			System.out.println(builder);
		});
	}

	@Test
	public void test5(){
		Class<User> userClass = User.class;
		Arrays.stream(userClass.getMethods()).forEach(method -> {
			Class<?>[] parameterTypes = method.getParameterTypes();

		});
	}

	@Test
	public void test6() {
		Class<ReflectDemo1> demo1 = ReflectDemo1.class;
		try {
			ReflectDemo1 reflectDemo1 = demo1.newInstance();
			Method getDemo = demo1.getMethod("getDemo");
			getDemo.invoke(reflectDemo1);
			Method saveDemo = demo1.getMethod("saveDemo");
			saveDemo.invoke(reflectDemo1);
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
