package com.murabahaclub.DocuSign.Config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author sradhakrishnan2
 *
 */

@Configuration
public class DocuSignConfig {

	@Value("${docusign.baseUrl}")
	private String docusignURL; 
	
	@Value("${docusign.username}")
	private String docusignUsername; 
	
	@Value("${docusign.password}")
	private String docusignPassword; 
	
	@Value("${docusign.ikey}")
	private String docusignikey;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
		
    	return builder.build();
	   
	}

	

	
}
