package com.gpdata.template.elasticsearch;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gpdata.template.utils.ConfigUtil;
import com.gpdata.template.utils.TransformUtil;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
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

import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by acer_liuyutong on 2017/5/23.
 */
public class Elasticsearch2XUtil {

	private static final Logger logger = LoggerFactory.getLogger(Elasticsearch2XUtil.class);

	/**
	 * 利用Spring的restTemplate进行REST资源交互
	 */
	private static RestTemplate restTemplate;


	/**
	 * 请求头信息
	 */
	private static HttpHeaders headers;

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

	//getter和setter方法
	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public static void setRestTemplate(RestTemplate restTemplate) {
		Elasticsearch2XUtil.restTemplate = restTemplate;
	}

	public static HttpHeaders getHeaders() {
		return headers;
	}

	public static void setHeaders(HttpHeaders headers) {
		Elasticsearch2XUtil.headers = headers;
	}

	/**
	 * 把实体转换成Http传送的报文
	 * @param body 实体类对象
	 */
	private static HttpEntity<String> generateHttpEntity(Object body){
		if (body == null) return null;
		return new HttpEntity<>(JSONObject.toJSONString(body), headers);
	}

	/**
	 * 把String转换成Http传送的报文
	 * @param body 实体类对象
	 */
	private static HttpEntity<String> generateHttpEntity(String body){
		if (StringUtils.isBlank(body)) return null;
		return new HttpEntity<>(body, headers);
	}

	/**
	 * 创建索引
	 * @param index 索引名称
	 */
	public static void save(String index){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isBlank(index)){
			return;
		}
		url += "/" + index;
		restTemplate.put(url,null);
	}

	/**
	 * 创建索引和类型
	 * @param index 索引名称
	 * @param type 类型名称
	 */
	public static void save(String index, String type){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type)){
			return;
		}
		if(!isExists(index)){
			return;
		}
		url += "/" + index + "/_mapping/" + type;
		String json = "{\"properties\" : {}}";
		HttpEntity<String> formEntity = generateHttpEntity(json);
		restTemplate.put(url,formEntity);
	}

	/**
	 * 创建索引、类型、文档--只支持插入一条数据
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 文档实体对象
	 * @param id 指定文档存入的Id，如果不指定Id，ES则自动生成Id
	 */
	public static void save(String index, String type, String id, Object body){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type;
		HttpEntity<String> formEntity = generateHttpEntity(body);

		//id不为空，用PUT请求
		url += "/" + id;
		restTemplate.put(url,formEntity);
	}

	/**
	 * 创建索引、类型、文档--只支持插入一条数据
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 文档实体对象
	 * 如果不指定Id，ES则自动生成Id
	 */
	public static void save(String index, String type, Object body){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type;
		HttpEntity<String> formEntity = generateHttpEntity(body);

		//id为空，用POST请求
		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 根据ID更新文档--完全更新
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 文档实体对象
	 */
	public static void update(String index, String type, String id, Object body){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type + "/" + id;
		HttpEntity<String> formEntity = generateHttpEntity(body);

		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 根据ID更新文档--可选择性的更新
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 文档实体对象
	 * @param partial 是否局部更新
	 */
	public static void update(String index, String type, String id, Object body, boolean partial){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type + "/" + id;
		HttpEntity<String> formEntity = generateHttpEntity(body);

		if (partial){
			url += "/_update";
			formEntity = generateHttpEntity(createPartialUpdateJson(TransformUtil.object2Map(body)));
		}

		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 根据ID更新文档--局部更新
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 需要更新的kv集合
	 */
	public static void update(String index, String type, String id, Map<String, Object> body){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null || body.isEmpty()){
			//文档实体对象不能为空
			return;
		}

		url += "/" + index + "/" + type + "/" + id + "/_update";
		logger.debug(url);
		HttpEntity<String> formEntity = generateHttpEntity(createPartialUpdateJson(body));

		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 删除索引
	 * @param index 索引
	 */
	public static void delete(String index){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isBlank(index) || !isExists(index)){
			return;
		}
		url += "/" + index;
		restTemplate.delete(url);
	}

	/**
	 * 删除索引、类型
	 * @param index 索引
	 * @param type 类型
	 */
//	@Deprecated
//	public static void delete(String index, String type){
//		if(StringUtils.isAnyBlank(index, type)){
//			return;
//		}
//		url += "/" + index + "/" + type;
//		restTemplate.delete(url);
//	}

	/**
	 * 删除索引、类型、文档
	 * @param index 索引
	 * @param type 类型
	 * @param id 文档Id
	 */
	public static void delete(String index, String type, String id){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isAnyBlank(index, type, id)){
			//TODO 抛异常
			return;
		}
		url += "/" + index + "/" + type + "/" + id;
		restTemplate.delete(url);
	}

	/**
	 * 使用bulk机制批量插入
	 * @param index
	 * @param type
	 * @param bodies
	 * @return
	 */
	public static String bulkIndex(String index, String type, List<Map<String, Object>> bodies){
		String url = ConfigUtil.getConfig("ES.url");
		HttpEntity<String> formEntity = generateHttpEntity(createBulkIndexJson(bodies));

		if(formEntity.getBody() == null){
			return null;
		}

		url += "/" + index + "/" + type + "/_bulk";

		return restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 检查索引是否存在
	 * @param index 索引
	 * @return 是否存在
	 */
	public static boolean isExists(String index){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isBlank(index)){
			return false;
		}
		url += "/" + index;
		try{
			restTemplate.headForHeaders(url);
		}catch (Exception e){
			return false;
		}
		return true;
	}

	/**
	 * 检查索引、类型是否存在
	 * @param index 索引
	 * @param type 类型
	 * @return 是否存在
	 */
	public static boolean isExists(String index, String type){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(index, type)){
			return false;
		}
		url += "/" + index + "/" + type;
		try{
			restTemplate.headForHeaders(url);
		}catch (Exception e){
			return false;
		}
		return true;
	}

	/**
	 * 检查索引、类型、文档是否存在
	 * @param index 索引
	 * @param type 类型
	 * @param id 文档Id
	 * @return 是否存在
	 */
	public static boolean isExists(String index, String type, String id){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(index, type, id)){
			return false;
		}
		url += "/" + index + "/" + type + "/" + id;
		try{
			restTemplate.headForHeaders(url);
		}catch (Exception e){
			return false;
		}
		return true;
	}

	/**
	 * 简单查询，搜索全部字段
	 * @param keyWord 搜索关键词
	 * @return 结果
	 */
	public static String queryString(String keyWord){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(keyWord)){
			return null;
		}
		url += "/_search?q=*" + keyWord + "*";
		logger.debug(url);
		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 简单查询，搜索全部字段
	 * @param keyWord 搜索关键词
	 * @param from 分页起始
	 * @param size 条数
	 * @return 结果
	 */
	public static String queryString(String keyWord, int from ,int size){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(keyWord)){
			return null;
		}
		url += "/_search?q=*" + keyWord + "*&from=" + from + "&size=" + size;
		logger.debug(url);
		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 简单查询，搜索全部字段
	 * @param keyWord 搜索关键词
	 * @param from 分页起始
	 * @param size 条数
	 * @return 结果
	 */
	public static String queryWithoutSource(String keyWord, int from ,int size){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(keyWord)){
			return null;
		}
		url += "/_search?q=*" + keyWord + "*&_source=false&from=" + from + "&size=" + size;
		logger.debug(url);
		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 简单查询，搜索索引下的全部字段
	 * @param index 索引
	 * @param keyWord 搜索关键词
	 * @param from 分页起始
	 * @param size 条数
	 * @return 结果
	 */
	public static String queryString(String index, String keyWord, int from ,int size){
		String url = ConfigUtil.getConfig("ES.url");
		if (StringUtils.isAnyBlank(index, keyWord)){
			return null;
		}
		url += "/" + index + "/_search?q=*" + keyWord + "*&from=" + from + "&size=" + size;
		logger.debug(url);
		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 复合查询-GET请求不太靠谱
	 * @param index
	 * @param keyWord
	 * @param params
	 * @param startDate
	 * @param endDate
	 * @param from
	 * @param size
	 * @return
	 */
	public static String query(String index, String keyWord, Map<String, String> params, Long startDate, Long endDate, Integer from, Integer size){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isNotBlank(index)){
			url += "/" + index;
		}
		url += "/_search?q=";
		if(StringUtils.isNotBlank(keyWord)){
			url += "+(*"+keyWord+"*)";
		}
		if (params != null && !params.isEmpty()){
			Set<Map.Entry<String, String>> entries = params.entrySet();
			for (Map.Entry<String, String> entry:entries) {
				String key = entry.getKey();
				String value = entry.getValue();
				if(StringUtils.isNoneBlank(key, value)){
					url += " +" + key + ":\"" + value + "\"";
				}
			}
		}
		if(startDate != null){
			url += " +dateTimestamp:>"+startDate;
		}
		if(endDate != null){
			url += " +dateTimestamp:<"+endDate;
		}
		url += "&from=" + from + "&size=" + size;

		logger.debug("url: {}",url);

		return restTemplate.getForObject(url, String.class);
	}

	/**
	 * 复合查询
	 * @param index
	 * @param type
	 * @param industry
	 * @param region
	 * @param tags
	 * @return
	 */
	public static String queryWithDsl(String index, String type, String industry, String region, String tags){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isNotBlank(index)){
			url += "/" + index;
		}
		if(StringUtils.isNotBlank(type)){
			url += "/" + type;
		}
		url += "/_search?";
		if(StringUtils.isNotBlank(industry)){
		}
		if(StringUtils.isNotBlank(region)){
		}
		if(StringUtils.isNotBlank(tags)){
		}
		logger.debug("url: {}",url);

		return null;
	}

	/**
	 * 请求体查询
	 * @param index 索引
	 * @param requestBody 请求体
	 * @param from 分页
	 * @param size 分页
	 * @return 查询结果
	 */
	public static String queryWithDsl(String index, String requestBody, Integer from, Integer size){
		logger.debug("requestBody: {}",requestBody);
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isNotBlank(index)){
			url += "/" + index;
		}
		url += "/_search?from=" + from + "&size=" + size;

		logger.debug("url: {}",url);
		HttpEntity<String> formEntity = generateHttpEntity(requestBody);
		return restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 按field字段分组
	 * @param keyWord
	 * @param index
	 * @param type
	 * @param field
	 * @param size
	 * @return
	 */
	public static String queryWithAggs(String keyWord, String index, String type, String field, int size){
		String url = ConfigUtil.getConfig("ES.url");
		if(StringUtils.isNotBlank(index)){
			url += "/" + index;
		}
		if(StringUtils.isNotBlank(type)){
			url += "/" + type;
		}
		url += "/_search?search_type=count";
		logger.debug("url: {}",url);

		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
		boolQueryBuilder.must(QueryBuilders.queryStringQuery(keyWord).phraseSlop(0));
		searchSourceBuilder.query(boolQueryBuilder);

		SearchSourceBuilder query = searchSourceBuilder.aggregation(AggregationBuilders.terms("groupByType").field(field).size(size));

		HttpEntity<String> formEntity = generateHttpEntity(query.toString());
		return restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 创建bulk批量执行的JSON格式
	 * @param bodies
	 * @return
	 */
	private static String createBulkIndexJson(List<Map<String, Object>> bodies){
		if(bodies == null || bodies.isEmpty()){
			return null;
		}
		StringBuilder result = new StringBuilder();
		for (Map<String, Object> body : bodies) {
			if(body.get("id") == null){
				result.append("{\"index\":{}}\n");
			}
			result.append("{\"index\":{\"_id\":").append(body.get("id")).append("}}\n");
			result.append(JSONObject.toJSONString(body)).append("\n");

		}
		return result.toString();
	}

	/**
	 * 创建局部更新的JSON格式
	 * @return
	 */
	private static String createPartialUpdateJson(Map<String, Object> body){
		if (body == null || body.isEmpty()){
			return null;
		}
		Map<String, Object> result = new HashMap<>();
		result.put("doc",body);
		return JSONObject.toJSONString(result);
	}

	/**
	 * 解析出hits中的total和rows
	 */
	public static Map<String, Object> parseHits(String result){
		Map<String, Object> map = new HashMap<>();

		if (StringUtils.isBlank(result)) {
			return map;
		}
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		JSONObject hits = (JSONObject) jsonObject.get("hits");
		Integer total = hits.getInteger("total");
		JSONArray subHits = (JSONArray) hits.get("hits");
		JSONArray parseArray = JSON.parseArray(subHits.toJSONString());
		List<Map<String, Object>> rowList = new ArrayList<>();
		for (Object aParseArray : parseArray) {
			JSONObject json = (JSONObject) aParseArray;
			JSONObject jsonSource = (JSONObject) json.get("_source");
			Map<String, Object> temp = (Map<String, Object>) JSONObject.parse(jsonSource.toJSONString());
			rowList.add(temp);

		}

		map.put("total",total);
		map.put("rows",rowList);
		return map;
	}

	/**
	 * 解析出hits中的total和rows
	 */
	public static Map<String, Object> groupByType(String result, int offset, int limit){
		Map<String, Object> map = new HashMap<>();

		if (StringUtils.isBlank(result)) {
			return map;
		}
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		JSONObject hits = (JSONObject) jsonObject.get("hits");
		JSONArray subHits = (JSONArray) hits.get("hits");

		//按照表名称分组
		Map<Object, Map<Object, Integer>> tableCount = subHits.stream().collect(Collectors.groupingBy(one ->
						(JSONObject.parseObject(one.toString()).get("_type")),
				Collectors.groupingBy(body -> JSONObject.parseObject(body.toString()).get("_index"), Collectors.summingInt(p -> 1))
		));

		List<Map.Entry<Object, Map<Object, Integer>>> entries = new ArrayList<>(tableCount.entrySet());

		List<Map<String, Object>> sortedList = entries.stream().flatMap(one -> {
			List<Map<String, Object>> list = new ArrayList<>();
			Object type = one.getKey();
			Map<Object, Integer> value = one.getValue();
			value.forEach((index, count) -> {
				Map<String, Object> temp = new HashMap<>();
				temp.put("type", type);
				temp.put("index", index);
				temp.put("value", count);
				list.add(temp);
			});
			return list.stream();
		}).sorted((a, b) -> {    //排序
			Integer value_a = Integer.parseInt(a.get("value").toString());
			Integer value_b = Integer.parseInt(b.get("value").toString());
			if (value_a > value_b) {
				return -1;
			} else if (Objects.equals(value_a, value_b)) {
				return 0;
			} else {
				return 1;
			}
		}).collect(Collectors.toList());


		//用于分页
		int size = sortedList.size();
		int toIndex = offset + limit;
		if(toIndex > size){
			toIndex = size;
		}
		List<Map<String, Object>> subList = sortedList.subList(offset, toIndex);

		map.put("total",sortedList.size());
		map.put("rows",subList);
		return map;
	}

	public static Map<String, Object> getTypeDetails(String result,String index, String type, int offset, int limit){
		Map<String, Object> map = new HashMap<>();

		if (StringUtils.isBlank(result)) {
			return map;
		}
		JSONObject jsonObject = (JSONObject) JSON.parse(result);
		JSONObject hits = (JSONObject) jsonObject.get("hits");
		JSONArray subHits = (JSONArray) hits.get("hits");

		//按照表名称分组
		Map<Object, Map<Object, List<Object>>> tableGroup = subHits.stream().collect(Collectors.groupingBy(one ->
						(JSONObject.parseObject(one.toString()).get("_type")),
				Collectors.groupingBy(body -> JSONObject.parseObject(body.toString()).get("_index"))
		));


		//得到指定type的详情列表
		List<Object> typeDetails = tableGroup.get(type).get(index);
		if(typeDetails == null || typeDetails.isEmpty()){
			return map;
		}
		int size = typeDetails.size();

		//排序
		List<JSONObject> sortedList = typeDetails.stream().map(obj -> (JSONObject) obj).sorted((a, b) -> {

			if (a.get("_score") == null || b.get("_score") == null) {
				return 0;
			}

			double value_a = Double.parseDouble(a.get("_score").toString());
			double value_b = Double.parseDouble(b.get("_score").toString());

			if (value_a > value_b) {
				return -1;
			} else if (Objects.equals(value_a, value_b)) {
				return 0;
			} else {
				return 1;
			}
		}).collect(Collectors.toList());

		//只保留_source部分
		List<Object> sourceList = sortedList.stream().map(one -> one.get("_source")).collect(Collectors.toList());

		//用于分页
		int toIndex = offset + limit;
		if(toIndex > size){
			toIndex = size;
		}
		List<Object> subList = sourceList.subList(offset, toIndex);

		map.put("total",size);
		map.put("rows",subList);
		return map;
	}
}

