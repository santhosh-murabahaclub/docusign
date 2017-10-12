package com.murabahaclub.DocuSign.ServiceImpl;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.murabahaclub.DocuSign.Service.DocuSignService;

@Service
public class DocuSignServiceImpl implements DocuSignService {
	
	@Value("${docusign.baseUrl}")
	private String docusignURL; 
	
	@Value("${docusign.username}")
	private String docusignUsername; 
	
	@Value("${docusign.password}")
	private String docusignPassword; 
	
	@Value("${docusign.ikey}")
	private String docusignikey;
	

	 private RestTemplate rt;

	
	ObjectMapper mapper=new ObjectMapper();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public ResponseEntity<String> createEnvelope(String envelopeData) {
		// TODO Auto-generated method stub
		ResponseEntity<String> resp, envelopeResponse = null;
		 try {
			 	resp=getAPICall(docusignURL+"login_information");
			 	String response=resp.getBody();
	        	JsonNode nObject = mapper.readTree(response);
	        	JsonNode baseObject=nObject.get("loginAccounts").get("baseUrl");
	        	//JsonNode accountObject=nObject.get("loginAccounts").get("accountId");
	        	String baseurl=baseObject.toString();
	        	//String accountID=accountObject.toString();
	        	JsonNode mainObject = mapper.readTree(envelopeData);
	        	envelopeResponse=getAPICall(baseurl+"/envelopes", mainObject.toString());
	        	//int envelopeStatus=envelopeResponse.getStatusCodeValue();
	        	String envelopeResp=envelopeResponse.getBody();
	        	
	        	
	        	
	        	logger.info(envelopeResp);
		 }catch (IOException e){
			 e.printStackTrace();
		 }
		 logger.info(envelopeResponse.toString());
		return envelopeResponse;
	}
	
	

	public ResponseEntity<String> getAPICall(String url){
		
		String headerValue = "{\"Username\":\"" +  docusignUsername + "\",\"Password\":\"" +  docusignPassword + "\",\"IntegratorKey\":\"" +  docusignikey + "\"}";
		HttpHeaders headers=new HttpHeaders();
    	headers.add("X-DocuSign-Authentication", headerValue);
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.add("Accept","application/json");
    	HttpEntity<String>request = new HttpEntity<String>(headers);
    	logger.info(url);
    	
    	ResponseEntity<String> resp=rt.exchange(url, HttpMethod.GET, request, String.class);
		return resp;		
	}
	
public ResponseEntity<String> getAPICall(String url, String envelopeDatas){
		
		String headerValue = "{\"Username\":\"" +  docusignUsername + "\",\"Password\":\"" +  docusignPassword + "\",\"IntegratorKey\":\"" +  docusignikey + "\"}";
		HttpHeaders headers=new HttpHeaders();
    	headers.add("X-DocuSign-Authentication", headerValue);
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<String> request = new HttpEntity<String>(envelopeDatas, headers);
    	ResponseEntity<String> resp=null;
    	resp=rt.exchange(url, HttpMethod.POST, request, String.class);
		return resp;		
	}


}
