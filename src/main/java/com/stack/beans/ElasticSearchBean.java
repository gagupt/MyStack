package com.stack.beans;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = { "com.stack.services" })
public class ElasticSearchBean {

	@Bean
	public RestHighLevelClient getRestHighLevelClient() {
		RestHighLevelClient client = new RestHighLevelClient(
				RestClient.builder(new HttpHost("localhost", 9200, "http"), new HttpHost("localhost", 9201, "http")));
		return client;
	}
}
