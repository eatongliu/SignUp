package com.gpdata.template.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import com.gpdata.template.utils.ConfigUtil;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by acer_liuyutong on 2017/5/23.
 */
public class ElasticsearchUtil {

	private static final Logger logger = LoggerFactory.getLogger(ElasticsearchUtil.class);

	/**
	 * 利用Spring的restTemplate进行REST资源交互
	 */
	private static RestTemplate restTemplate;

	/**
	 * 消息体-用来表征一个http报文的实体
	 */
	private static HttpEntity<String> formEntity = null;

	/**
	 * 请求头信息
	 */
	private static HttpHeaders headers;

	/**
	 * es请求地址
	 */
	private static String url;

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

		url = ConfigUtil.getConfig("ES.url");
	}

	//getter和setter方法
	public static RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public static void setRestTemplate(RestTemplate restTemplate) {
		ElasticsearchUtil.restTemplate = restTemplate;
	}

	public static HttpEntity<String> getFormEntity() {
		return formEntity;
	}

	public static void setFormEntity(HttpEntity<String> formEntity) {
		ElasticsearchUtil.formEntity = formEntity;
	}

	public static HttpHeaders getHeaders() {
		return headers;
	}

	public static void setHeaders(HttpHeaders headers) {
		ElasticsearchUtil.headers = headers;
	}

	public static String getUrl() {
		return url;
	}

	public static void setUrl(String url) {
		ElasticsearchUtil.url = url;
	}

	/**
	 * 把实体转换成Http传送的报文
	 * @param body 实体类对象
	 */
	private static void generateHttpEntity(Object body){
		if (body == null) return;
		formEntity = new HttpEntity<>(JSONObject.toJSONString(body), headers);
	}

	/**
	 * 把String转换成Http传送的报文
	 * @param body 实体类对象
	 */
	private static void generateHttpEntity(String body){
		if (StringUtils.isBlank(body)) return;
		formEntity = new HttpEntity<>(body, headers);
	}

	/**
	 * 创建索引
	 * @param index 索引名称
	 */
	public static void save(String index){
		if(StringUtils.isBlank(index)){
			//TODO 抛异常
			return;
		}
		url += "/" + index;
		restTemplate.put(url,formEntity);
	}

	/**
	 * 创建索引和类型
	 * @param index 索引名称
	 * @param type 类型名称
	 */
	public static void save(String index, String type){
		if(StringUtils.isAnyBlank(index, type)){
			return;
		}
		url += "/" + index + "/" + type;
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
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type;
		generateHttpEntity(body);

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
		if(StringUtils.isAnyBlank(index, type)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type;
		generateHttpEntity(body);

		//id为空，用POST请求
		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 根据ID更新文档--完全更新
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 文档实体对象
	 * 如果不指定Id，ES则自动生成Id
	 */
	public static void update(String index, String type, String id, Object body){
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null){
			//文档实体对象不能为空
			return;
		}
		url += "/" + index + "/" + type + "/" + id;
		generateHttpEntity(body);

		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 根据ID更新文档--局部更新
	 * @param index 索引名称
	 * @param type 类型名称
	 * @param body 需要更新的kv集合
	 * 如果不指定Id，ES则自动生成Id
	 */
	public static void update(String index, String type, String id, Map<String, Object> body){
		if(StringUtils.isAnyBlank(index, type, id)){
			return;
		}
		if(body == null || body.isEmpty()){
			//文档实体对象不能为空
			return;
		}

		url += "/" + index + "/" + type + "/" + id + "/_update";
		logger.debug(url);
		generateHttpEntity(createPartialUpdateJson(body));

		restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 删除索引
	 * @param index 索引
	 */
	public static void delete(String index){
		if(StringUtils.isBlank(index)){
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
	@Deprecated
	public static void delete(String index, String type){
		if(StringUtils.isAnyBlank(index, type)){
			return;
		}
		url += "/" + index + "/" + type;
		restTemplate.delete(url);
	}

	/**
	 * 删除索引、类型、文档
	 * @param index 索引
	 * @param type 类型
	 * @param id 文档Id
	 */
	public static void delete(String index, String type, String id){
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
	public static String bulkIndex(String index, String type, List<?> bodies){

		generateHttpEntity(createBulkIndexJson(bodies));

		if(formEntity.getBody() == null){
			return null;
		}

		url += "/" + index + "/" + type + "_bulk";

		return restTemplate.postForObject(url, formEntity, String.class);
	}

	/**
	 * 创建bulk批量执行的JSON格式
	 * @param bodies
	 * @return
	 */
	private static String createBulkIndexJson(List<?> bodies){
		if(bodies == null || bodies.isEmpty()){
			return null;
		}
		String baseBody = "{\"index\":{}}\n";
		return bodies.stream().map(body -> (baseBody+JSONObject.toJSONString(body) + "\n")).reduce((a, b) -> (a + b)).get();
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

}
