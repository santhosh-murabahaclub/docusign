/*package com.murabahaclub.DocuSign;

import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.oltu.oauth2.client.HttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;



@Configuration
public class RESTConfig {

	@Value("${sf.host}")
	private String sfHost;

	@Value("${httpclient.pool.connection.max.value}")
	private int poolMaxConnection;

	@Value("${httpclient.pool.connection.max.per.route}")
	private int poolMaxConnectionPerRoute;

	@Value("${httpclient.pool.connection.timeout}")
	private int poolConnectionTimeout;
	
	@Value("${httpclient.pool.connection.idle.timeout}")
	private int poolIdleConnectionTimeout;

	@Bean("commonClientHttpRequestFactory")
	public ClientHttpRequestFactory httpRequestFactory() {
		return new HttpComponentsClientHttpRequestFactory(commonHttpClient());
	}
	@Bean("simpleRestTemplate")
	public RestTemplate commonRestTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	@Bean("commonHttpClient")
	public org.apache.http.client.HttpClient commonHttpClient() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(poolMaxConnection);
		connectionManager.setDefaultMaxPerRoute(poolMaxConnectionPerRoute);
		connectionManager.closeIdleConnections(poolIdleConnectionTimeout, TimeUnit.MILLISECONDS);
		connectionManager.setValidateAfterInactivity(poolIdleConnectionTimeout);
		connectionManager.setMaxPerRoute(new HttpRoute(new HttpHost(sfHost, 443)), poolMaxConnectionPerRoute);

		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(poolConnectionTimeout).build();

		return HttpClientBuilder.create().setConnectionManager(connectionManager).setDefaultRequestConfig(requestConfig)
				.build();
	}
}
*/