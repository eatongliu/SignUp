package com.gpdata.template.utils;

import org.junit.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by chengchao on 17-5-11.
 */
public class Excel2007UtilTest {


    @Test
    public void TestUtil() {

        List<String> header = Arrays.asList("姓名", "年龄", "生日", "记录创建时间", "婚否", "备注");

        List<Object> list1 = new ArrayList<>();
        list1.add("程超");
        list1.add(18);
        list1.add(Calendar.getInstance());
        list1.add(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        list1.add(true);
        list1.add(null);

        List<Object> list2 = new ArrayList<>();
        list2.add("韩冬");
        list2.add(18);
        list2.add(Calendar.getInstance());
        list2.add(new Date());
        list2.add(true);
        list2.add(null);

        try (OutputStream outputStream = new FileOutputStream("D:\\a.xlsx")) {
//            ExcelDataBean excelDataBean = new ExcelDataBean.Builder()
//                    .addHeaderList(header)
//                    .addHeaderList(header)
//                    .addRowList(list1)
//                    .addRowList(list2)
//                    .build();

			List<List<Object>> lists = new ArrayList<>();
			lists.add(list1);
			lists.add(list2);
	        ExcelDataBean.Builder builder = new ExcelDataBean.Builder();

	        builder.addHeaderList(header);

	        lists.forEach(builder::addRowList);

	        builder.addRowList(list1);
	        builder.addRowList(list2);

	        ExcelDataBean excelDataBean = builder.build();

	        Excel2007Util.createExcel(excelDataBean, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
