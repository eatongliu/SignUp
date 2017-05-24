package com.gpdata.template.elasticsearch;

import com.gpdata.template.user.entity.User;
import com.gpdata.template.utils.ConfigUtil;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;

/**
 * Created by acer_liuyutong on 2017/5/23.
 */
public class EsQuery01 {
	private static final Logger logger = LoggerFactory.getLogger(EsQuery01.class);
	public static void main(String[] args) {

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		searchSourceBuilder.query(matchAllQuery());

		System.out.println(searchSourceBuilder);

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
		headers.setContentType(type);
		headers.add("Accept", MediaType.APPLICATION_JSON.toString());
		HttpEntity<String> formEntity = new HttpEntity<String>(searchSourceBuilder.toString(), headers);
		String result = null;
		try {
			String strESUrl = ConfigUtil.getConfig("ES.url") + "_search?size=" + 0 + "&from=" + 10;
			logger.debug("strESUrl : {}", strESUrl);
			result = restTemplate.postForObject(strESUrl, formEntity, String.class);
		} catch (HttpClientErrorException e) {
			logger.error("Exception:{}", e);
			throw new RuntimeException("请求ES数据不存在！");
		} catch (Exception e) {
			logger.error("Exception:{}", e.getCause());
			throw new RuntimeException("请求ES发生异常！");
		}
		System.out.println(result);
	}

	@Test
	public void testupdate1(){
		User user = new User();
		user.setUsername("lyt8");
		user.setPassword("123456");
		user.setUserId(8L);
		user.setEmail("654166357@qq.com");
		user.setCreateDate(new Date());
		ElasticsearchUtil.update("lyt", "test","AVw8WKxP0RKENlnQIP00", user);
	}

	@Test
	public void testupdate2(){
		Map<String, Object> map = new HashMap<>();
		map.put("username","lyt01");
		ElasticsearchUtil.update("lyt", "test", "AVw8AmGD0RKENlnQIP0o", map);
	}



}
