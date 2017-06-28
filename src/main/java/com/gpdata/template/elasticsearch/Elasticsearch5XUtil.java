package com.gpdata.template.elasticsearch;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

/**
 * Created by acer_liuyutong on 2017/5/31.
 * Elasticsearch 5.4.0
 */
public class Elasticsearch5XUtil {
	private static final Logger logger = LoggerFactory.getLogger(Elasticsearch5XUtil.class);

	private static RestClient restClient;

	static {
		restClient = RestClient.builder(new HttpHost("192.168.1.147", 9200, "http"))
				.build();
	}


	public static void closeClient() {
		try {
			restClient.close();
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getCause());
		}
	}

	public static RestClient getRestClient() {
		return restClient;
	}

	public static void createIndex(String index) {
		try {
			HttpEntity entity = new NStringEntity(customAnalysis(), ContentType.APPLICATION_JSON);
			restClient.performRequest("PUT", "/" + index, Collections.emptyMap(), entity);
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getMessage());
		}
	}

	public static void createType(String index, String type) {
		String url = "/" + index + "/" + type;
		try {
			HttpEntity entity = new NStringEntity(customMappings(), ContentType.APPLICATION_JSON);
			restClient.performRequest("PUT", url, Collections.emptyMap(), entity);
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getMessage());
		}
	}

	public static void deleteIndex(String index) {
		try {
			restClient.performRequest("DELETE", "/" + index);
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getMessage());
		}
	}


	/**
	 * 自定义分词解析器
	 *
	 * @return
	 */
	public static String customAnalysis() {
		String result = null;
		try {
			XContentBuilder xContentBuilder = XContentFactory.jsonBuilder()
					.startObject()
					.startObject("analysis")
					.startObject("analyzer")
					.startObject("by_smart")
					.field("type", "custom")
					.field("tokenizer", "ik_smart")
					.array("filter", "f_stop", "f_synonym", "f_pinyin")
					.array("char_filter", "cf_mapping")
					.endObject()
					.startObject("by_max_word")
					.field("type", "custom")
					.field("tokenizer", "ik_max_word")
					.array("filter", "f_stop", "f_synonym", "f_pinyin")
					.array("char_filter", "cf_mapping")
					.endObject()
					.endObject()
					.startObject("filter")
					.startObject("f_pinyin")
					.field("type", "pinyin")
					.field("first_letter", "prefix")
					.field("padding_char", " ")
					.endObject()
					.startObject("f_synonym")
					.field("type", "dynamic_synonym")
					.field("synonyms_path", "analysis/synonyms.txt")
					.endObject()
					.startObject("f_stop")
					.field("type", "stop")
					.array("stopwords", " ")
					.endObject()
					.endObject()
					.startObject("char_filter")
					.startObject("cf_mapping")
					.field("type", "mapping")
					.array("mappings", "| => |")
					.endObject()
					.endObject()
					.endObject()
					.endObject();
			result = xContentBuilder.string();
			logger.debug("Es自定义分词解析器: {}", result);
			xContentBuilder.close();
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getMessage());
		}
		return result;
	}

	/**
	 * 自定义mappings
	 */
	public static String customMappings() {
		String result = null;
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder();
			builder.startObject()
					.startObject("properties");

			builder.startObject("title")
					.field("type", "text")
					.field("index", "analyzed")
					.field("analyzer", "by_max_word")
					.field("search_analyzer", "by_smart")
					.endObject();


			builder.endObject()
					.endObject();


			result = builder.string();
		} catch (IOException e) {
			logger.error("Es异常: {}", e.getMessage());
		}
		return result;
	}

	public static void main(String[] args) {

	}
}