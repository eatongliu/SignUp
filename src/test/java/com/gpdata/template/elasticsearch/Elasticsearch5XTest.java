package com.gpdata.template.elasticsearch;

import org.elasticsearch.client.RestClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by acer_liuyutong on 2017/6/22.
 */
public class Elasticsearch5XTest {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	public void testGetClient() {
		RestClient restClient = Elasticsearch5XUtil.getRestClient();
		RestClient restClient2 = Elasticsearch5XUtil.getRestClient();
		RestClient restClient3 = Elasticsearch5XUtil.getRestClient();
		System.out.println(restClient);
		System.out.println(restClient2);
		System.out.println(restClient3);
	}

	@Test
	public void testCreateIndex() {
		Elasticsearch5XUtil.createIndex("girl");
		Elasticsearch5XUtil.createIndex("boy");
		Elasticsearch5XUtil.closeClient();
	}

	@Test
	public void testDeleteIndex() {
		Elasticsearch5XUtil.deleteIndex("girl");
		Elasticsearch5XUtil.deleteIndex("boy");
		Elasticsearch5XUtil.closeClient();
	}

	@Test
	public void testJson() {
		try {
			XContentBuilder builder = XContentFactory.jsonBuilder().startObject("aa").endObject();
			System.out.println(builder.string());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
