package utils;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.junit.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by acer_liuyutong on 2017/3/20.
 */
public class SimpleTest {

    @Test
    public void test1(){
        boolean b = new Integer(123) == new Integer(123);
        System.out.println(b);
    }
    @Test
    public void test2(){
        for (int i = 1;i<80;i++){
            System.out.print(i+",");
        }
    }
    @Test
    public void test3(){
        Random random = new Random(10);
        Random random2 = new Random(10);
        int num1 = random.nextInt();
        int num2 = random2.nextInt();
        System.out.println(num1);
        System.out.println(num2);
    }
    @Test
    public void test4(){
        List<Object> list = new ArrayList<>();
        list.add(1);
        list.add(null);
        list.add(2);
        System.out.println(list);
    }

    @Test
    public void test5() throws IOException, ParserConfigurationException {

        String url = "http://localhost:8080/v1/xmlCreate";
        RestTemplate restTemplate = new RestTemplate();

	    //创建根节点
	    Element root = DocumentHelper.createElement("root");
	    //创建一个xml文档
	    Document doc = DocumentHelper.createDocument(root);

	    MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();

//        File file = new File("1.txt");

	    //1.利用ByteArrayResource携带MultipartFile
//	    byte[] bytes = FileUtils.readFileToByteArray(file);
	    byte[] bytes = documentToByte(doc);
	    ByteArrayResource byteArrayResource = new ByteArrayResource(bytes){
		    @Override
		    public String getFilename(){
			    return "100.xml";
		    }
	    };

	    //2.利用FileSystemResource携带MultipartFile
//        Resource fileSystemResource = new FileSystemResource("1.txt");

//        form.add("parent_dir", uploadPath);
		form.add("xmlFile", byteArrayResource);


        String response = restTemplate.postForObject(url, form, String.class);
        System.out.println(response);
    }

    private byte[] documentToByte(Document document) throws IOException {
		//把document存放到stream中  true代表是否换行
	    OutputFormat format = new OutputFormat("    ", true);
	    format.setEncoding("utf-8");//设置编码格式

	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    XMLWriter xmlWriter = new XMLWriter(byteArrayOutputStream, format);
	    xmlWriter.write(document);
	    return byteArrayOutputStream.toByteArray();
    }


}
