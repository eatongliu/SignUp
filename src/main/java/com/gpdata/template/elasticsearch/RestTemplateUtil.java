package com.gpdata.template.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.gpdata.template.user.entity.User;
import com.gpdata.template.utils.ConfigUtil;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.nio.charset.Charset;
import java.util.*;

/**
 * Created by acer_liuyutong on 2017/5/23.
 */
public class RestTemplateUtil {
	private static final Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

	//利用Spring的restTemplate进行REST资源交互
	private static RestTemplate restTemplate;

	//消息体
	private static HttpEntity<String> formEntity;

	//请求头信息
	private static HttpHeaders headers;

	private static String url = ConfigUtil.getConfig("ES.url");

	static {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(Integer.parseInt(ConfigUtil.getConfig("httpClientFactory.connectTimeout")));
		clientHttpRequestFactory.setReadTimeout(Integer.parseInt(ConfigUtil.getConfig("httpClientFactory.readTimeout")));

		// 添加转换器
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
		messageConverters.add(new FormHttpMessageConverter());

		restTemplate = new RestTemplate(clientHttpRequestFactory);
		restTemplate.setMessageConverters(messageConverters);

		//请求头
		headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
		headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
	}

	@Test
	public void createIndex(){
		User user = new User();
		user.setUsername("lyt");
		user.setPassword("123456");
		user.setUserId(1L);
		user.setEmail("654166357@qq.com");
		user.setCreateDate(new Date());

		user = null;
		HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(user), headers);


		url += "/lyt2/test1";
		logger.debug("url: {}",url);
		restTemplate.put(url,formEntity);
	}

	@Test
	public void voidtest23(){
		url += "/lyt1/_mapping/test53";

		String json = "{\"properties\" : {}}";
		System.out.println(json);
		HttpEntity<String> formEntity = new HttpEntity<>(json, headers);

		restTemplate.put(url,formEntity);
	}
	@Test
	public void tes12(){
		Map<String, Object> map = new HashMap<>();
		map.put("properties", "{}");
		String json1 = JSONObject.toJSONString(map);
		System.out.println(json1);
	}

	@Test
	public void postDoc(){
		User user = new User();
		user.setUsername("lyt");
		user.setPassword("123456");
		user.setUserId(1L);
		user.setEmail("654166357@qq.com");
		user.setCreateDate(new Date());

		user = null;
		String tmp = "{}";

		HttpEntity<String> formEntity = new HttpEntity<>(JSONObject.toJSONString(user), headers);
//		HttpEntity<String> formEntity = new HttpEntity<>(tmp, headers);

		url += "/lyt/test";
		logger.debug("url: {}",url);
		logger.debug(formEntity.getBody());
		String resutl = restTemplate.postForObject(url, formEntity, String.class);
		System.out.println(resutl);
	}

	@Test
	public void delete(){
		url += "/lyt/test";
		restTemplate.delete(url);
	}

	@Test
	public void bulk(){
		User user = new User();
		user.setUsername("4lyt4");
		user.setPassword("123456");
		user.setUserId(44L);
		user.setEmail("654166357@qq.com");
		user.setCreateDate(new Date());

		User user2 = new User();
		user2.setUsername("5lyt5");
		user2.setPassword("123456");
		user2.setUserId(55L);
		user2.setEmail("654166357@qq.com");
		user2.setCreateDate(new Date());

		User user3 = new User();
		user3.setUsername("6lyt6");
		user3.setPassword("123456");
		user3.setUserId(66L);
		user3.setEmail("654166357@qq.com");
		user3.setCreateDate(new Date());
		List<User> users = new ArrayList<>();
		users.add(user);
		users.add(user2);
		users.add(user3);

		url += "/lyt/test/_bulk";
		logger.debug(url);

		String bulkBody = createBulkBody(users);
		logger.debug(bulkBody);

		HttpEntity<String> formEntity = new HttpEntity<>(bulkBody, headers);

		String result = restTemplate.postForObject(url, formEntity, String.class);
		System.out.println(result);

	}

	public static String createBulkBody(List<?> bodies){
		if(bodies.isEmpty()){
			return null;
		}
		String baseBody = "{\"index\":{}}\n";
		return bodies.stream().map(body -> (baseBody+JSONObject.toJSONString(body) + "\n")).reduce((a, b) -> (a + b)).get();
	}

	@Test
	public void test23(){
		String bulkBody = createBulkBody(Collections.emptyList());
		System.out.println(bulkBody);
	}

	public static void main(String[] args) {
		//QueryDSL查询语句，searchSourceBuilder 的toString方法就是JSONString
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		QueryBuilder qb = QueryBuilders.matchAllQuery();
		searchSourceBuilder.query(qb);

		HttpEntity<String> formEntity = new HttpEntity<>(searchSourceBuilder.toString(), headers);



		//多个index用逗号","隔开
		String index = "lyt";
		//多个type用逗号","隔开
		String type = "test";

		url += "/" + index + "/" + type + "/_search";

		logger.debug("url: {}",url);

		String response = restTemplate.postForObject(url, formEntity, String.class);
		System.out.println(response);
	}
}
