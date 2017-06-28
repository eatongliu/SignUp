package com.gpdata.template.io;

import java.io.File;

/**
 * Created by acer_liuyutong on 2017/6/26.
 */
public class PathDemo {

		public static void getPath() {
			//方式一
			System.out.println(System.getProperty("user.dir"));
			//方式二
			File directory = new File("");//设定为当前文件夹
			try{
				System.out.println(directory.getCanonicalPath());//获取标准的路径
				System.out.println(directory.getAbsolutePath());//获取绝对路径
			}catch(Exception e) {
				e.printStackTrace();
			}
			//方式三
			System.out.println(PathDemo.class.getResource("/"));
			System.out.println(PathDemo.class.getResource(""));
			//方式4
			System.out.println(PathDemo.class.getClassLoader().getResource(""));
			System.out.println(PathDemo.class.getClassLoader().getResource("source.xml"));
		}



	/**
		 * @param args
		 */
		public static void main(String[] args) {
			getPath();
		}



}
