package com.gpdata.template.command;

import com.alibaba.fastjson.JSONArray;
import com.gpdata.template.user.entity.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang.math.NumberUtils.toInt;
import static org.apache.tools.ant.taskdefs.Antlib.TAG;

/**
 * Created by acer_liuyutong on 2017/6/8.
 */
public class CommandTest {
	private static final Logger logger = LoggerFactory.getLogger(CommandTest.class);
	@Test
	public void test1(){
		try {
			String[] cmd = new String[]{"cmd","dir"};
			Process process = Runtime.getRuntime().exec(cmd);
			process.waitFor();

			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			String result = sb.toString();
			System.out.println(result);
		}
	    catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static synchronized int runShellCmd() {
		BufferedReader input = null;
		PrintWriter output = null;
		Process pro = null;
		try {
			pro = Runtime.getRuntime().exec("adb shell ");
			input = new BufferedReader(new InputStreamReader(pro.getInputStream()));
			pro.getOutputStream().write("pidof mediaserver\r\n".getBytes());
			pro.getOutputStream().flush();
			String line = input.readLine();
			int pid = 0;
			/**
			 * 按道理说直接执行命令打印是这样的：
			 * root@android:/ # adb shell
			 * root@android:/ # pidof mediaserver
			 * 7114
			 * 也就是说第三行就应该是我取到的pid值，但是实际上却是5行？
			 */
			for (int i = 0; i < 6; i++) {
				logger.error(TAG , i + " line is " + line);
				pid = toInt(line, 0);
				if (pid > 0)
					break;
				line = input.readLine();
			}
			logger.error(TAG, "pid:" + pid);
			/**
			 * 实际打印如下：
			 * E/MainActivity( 7036): 0 line is pidof mediaserver
			 * E/MainActivity( 7036): 1 line is
			 * E/MainActivity( 7036): 2 line is root@android:/ # pidof mediaserver
			 * E/MainActivity( 7036): 3 line is
			 * E/MainActivity( 7036): 4 line is 6946
			 * E/MainActivity( 7036): pid:6946
			 * 为什么会多出2个空行？？
			 */
			if (pid == 0) {
				throw new IOException("not find mediaserver process!");
			}
			String killCmd = String.format("kill -9 %d\r\n", pid);
			/**
			 * 直接这么使用不行的，不知道什么原因，执行结果死活不对。
			 */
			pro.getOutputStream().write(killCmd.getBytes());
			pro.getOutputStream().flush();

			/**
			 * 再一次这么重开就ok了，谁能告诉我原因？
			 */
			pro.destroy();
			pro = null;
			pro = Runtime.getRuntime().exec("adb shell ");
			pro.getOutputStream().write(killCmd.getBytes());
			pro.getOutputStream().flush();


		} catch (IOException ex) {
			ex.printStackTrace();
			return -1;
		} finally {
			try {
				if (input != null) {
					input.close();
				}
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (pro != null) {
				pro.destroy();
				pro = null;
			}
		}
		return 0;
	}

	@Test
	public void test2(){
		runShellCmd();
	}

	@Test
	public void test3(){
		User user = new User();
		user.setPassword("111");
		List list = new ArrayList();
		String s = JSONArray.toJSONString(user);
		System.out.println(s);
	}
}
