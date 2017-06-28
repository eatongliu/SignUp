package com.gpdata.template.elasticsearch;

import com.gpdata.template.user.entity.User;
import com.gpdata.template.utils.ConfigUtil;
import com.gpdata.template.utils.TransformUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.testng.annotations.Test;

import java.util.*;

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
		Elasticsearch2XUtil.update("lyt", "test","AVw8WKxP0RKENlnQIP00", user);
	}

	@Test
	public void testupdate2(){
		Map<String, Object> map = new HashMap<>();
		map.put("username","lyt01");
		Elasticsearch2XUtil.update("lyt", "test", "AVw8AmGD0RKENlnQIP0o", map);
	}

	@Test
	public void testupdate3(){
		User user = new User();
		user.setPassword("w3234");
		Elasticsearch2XUtil.update("lyt", "test", "AVw8AmGD0RKENlnQIP0o", user, false);
	}

	@Test
	public void testeteste(){

		User user = new User();
		user.setPassword("w3234");
		Map<String, Object> map = TransformUtil.object2Map(user);
		System.out.println(map);

	}

	@Test
	public void testQueryString(){
		String s = Elasticsearch2XUtil.queryString("lyt", "lyt", 0, 10);
		System.out.println(s);
	}

	@Test
	public void deleteIndex(){
		Elasticsearch2XUtil.delete("lyt1");
	}

	@Test
	public void testSaveType(){
		Elasticsearch2XUtil.save("lyt6","test6");
	}

	@Test
	public void saveTest(){
		QueryBody body1 = new QueryBody("1","工程建设", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body2 = new QueryBody("2","工程", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body3 = new QueryBody("3","招投标", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body4 = new QueryBody("4","股权", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body5 = new QueryBody("5","土地使用权", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body6 = new QueryBody("6","数据总览", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		QueryBody body7 = new QueryBody("7","政府采购", new Date(1495694990000L), new Date(1495694990000L), "1", "1", "1", "1");
		List<Map<String, Object>> list = new ArrayList<>();
		list.add(TransformUtil.object2Map(body1));
		list.add(TransformUtil.object2Map(body2));
		list.add(TransformUtil.object2Map(body3));
		list.add(TransformUtil.object2Map(body4));
		list.add(TransformUtil.object2Map(body5));
		list.add(TransformUtil.object2Map(body6));
		list.add(TransformUtil.object2Map(body7));

		Elasticsearch2XUtil.bulkIndex("ic_dss","test6",list);
	}

	@Test
	public void testQuery(){
		Map<String, String> map = new HashMap<>();
		map.put("industry","工业");
		map.put("region","北京");
		map.put("tags","央企");

//		String query = Elasticsearch2XUtil.query("lyt5", "test6", "工业", "北京", "国企 央企");
//		String query = Elasticsearch2XUtil.query("lyt6","AAA",map,new Date(1495694880000L),new Date(),0,10);
//		System.out.println(query);
	}

	@Test
	public void test233(){
		String url = "http://192.168.1.55:9200/lyt6/_search?" +
				"q=" +
				"+(*AAA*)" +
				"+industry:\"工业\" +region:\"北京\" +tags:(\"央企\" \"国企\") " +
				"+dateTimestamp:>1495773000000 +dateTimestamp:<1495775000000 " +
				"&from=0&size=10";
		String url2 = "http://192.168.1.55:9200/lyt6/_search?" +
				"q=" +
				"+(*AAA*)" +
				"+industry:\"工业\" +region:\"北京\" +tags:\"央企\"" +
				"+dateTimestamp:>1495694880000 +dateTimestamp:<1495775891143" +
				"&from=0&size=10";
		System.out.println("****" + url);
		String result = Elasticsearch2XUtil.getRestTemplate().getForObject(url2, String.class);
		System.out.println(result);
	}

	@Test
	public void tetstClearTable(){
		Elasticsearch2XUtil.delete("lyt15");
	}

	@Test
	public void testsometing(){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.queryStringQuery("1").phraseSlop(0));

		searchSourceBuilder.query(boolQueryBuilder).from(0).size(100);

		TermsBuilder one = AggregationBuilders.terms("groupBytype").field("_type").size(100);

		SearchSourceBuilder query = searchSourceBuilder.aggregation(one);
		System.out.println(query);
	}

	@Test
	public void testsometing2(){
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.queryStringQuery("1").escape(false));


		searchSourceBuilder.query(boolQueryBuilder).from(0).size(100);

		TermsBuilder one = AggregationBuilders.terms("groupBytype").field("_type").size(100);

		SearchSourceBuilder query = searchSourceBuilder.aggregation(one);
		System.out.println(query);
	}

}
