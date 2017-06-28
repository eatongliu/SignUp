package com.gpdata.template.io;

import org.junit.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

/**
 * Created by acer_liuyutong on 2017/6/26.
 */
public class IOTest {
	@Test
	public void test1() throws FileNotFoundException {
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw

            /* 读入TXT文件 */
			String pathname = "2.xml"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
			File filename = new File(pathname); // 要读取以上路径的input。txt文件
			InputStreamReader reader = new InputStreamReader(
					new FileInputStream(filename)); // 建立一个输入流对象reader
			BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
			String line;
			line = br.readLine();
			while (line != null) {
				line = br.readLine(); // 一次读入一行数据
			}

            /* 写入Txt文件 */
			File writename = new File("src/1.xml"); // 相对路径，如果没有则要建立一个新的output。txt文件
			boolean b = writename.createNewFile();// 创建新文件
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));
			out.write("我会写入文件啦\r\n"); // \r\n即为换行
			out.flush(); // 把缓存区内容压入文件
			out.close(); // 最后记得关闭文件

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test2() {
		String str = "this is just a test for stream.";
		try(ByteArrayInputStream is = new ByteArrayInputStream(str.getBytes());
		    FileOutputStream os = new FileOutputStream("1.txt")) {

			ReadableByteChannel channel = Channels.newChannel(is);
			FileChannel outChannel = os.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);

			while (channel.read(buffer) != -1) {
				buffer.flip();

				while (buffer.hasRemaining()) {
					System.out.println(buffer.get());
//					outChannel.write(buffer);
				}

				buffer.clear();
			}

			channel.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}