package com.murabahaclub.DocuSign.ServiceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.murabahaclub.DocuSign.Service.DocuSignService;
import com.ziggeo.Ziggeo;


/**
 * 
 * @author sradhakrishnan2
 *
 */


@Service
public class DocuSignServiceImpl implements DocuSignService {
	
	@Value("${docusign.baseUrl}")
	private String docusignURL; 
	
	@Value("${docusign.ebaseUrl}")
	private String docusignBURL; 
	
	@Value("${docusign.username}")
	private String docusignUsername; 
	
	@Value("${docusign.password}")
	private String docusignPassword; 
	
	@Value("${docusign.accountId}")
	private String docusignaccID; 
	
	@Value("${docusign.ikey}")
	private String docusignikey;
		
	@Value("${docusign.templateID}")
	private String docusigntID;
	
	
	
/*	@Value("${docusign.baseurl}")
	private String baseurl;;
	*/
	@Autowired
	private RestTemplate rt;
	
	
	
	ObjectMapper mapper=new ObjectMapper();
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	
	

	@Override
	public String createEnvelope(String envelopeData) {
		// TODO Auto-generated method stub
		ResponseEntity<String> resp, envelopeResponse = null;
		JsonNode nObject,bObject;
		String respUrl = null,recpUrl = null	;
		 try {
			 	resp=getAPICall(docusignURL+"login_information");
			 	String response=resp.getBody();
	        	nObject = mapper.readTree(response);
	        	bObject=nObject.get("loginAccounts").get(0);
	        	String baseurl=bObject.get("baseUrl").toString().replace("\"", "");
	        	logger.info("BaseURL:{}",baseurl);
	        	//JsonNode accountObject=nObject.get("loginAccounts").get("accountId");
	        	//=baseObject.toString();
	        	//String accountID=accountObject.toString();
	        	JsonNode mainObject = mapper.readTree(jsonFormats(envelopeData));	        	
	        	envelopeResponse=getAPICall(baseurl+"/envelopes", mainObject.toString());
	        	//int envelopeStatus=envelopeResponse.getStatusCodeValue();
	        	respUrl=envelopeResponse.getBody();
	        	
	        	logger.info("Envelope Creation Response:{}",respUrl);
	        	nObject = mapper.readTree(respUrl);
	        	respUrl=baseurl+nObject.get("uri").toString().replace("\"", "")+"/views/recipient";
	        	logger.info("Receipient request URL:{}",respUrl);
	        	nObject = mapper.readTree(signEmbed(respUrl));
	        	recpUrl=nObject.get("url").toString();
	        	logger.info("Receipient URL:{}",recpUrl);
	        	
		 }catch (IOException e){
			 e.printStackTrace();
		 }
		 //logger.info(envelopeResponse.toString());
		return recpUrl;
	}	
	
	@SuppressWarnings("unused")
	private String jsonFormats(String envelopeData) {
		String respdata =null;
		JsonNode bObject,jsArray,jArray;
		try {
			JsonNode mainObject = mapper.readTree(envelopeData);
			bObject=mainObject.get("docusign");
			
			
			jsArray=bObject.get("templateRoles");
			int x=jsArray.size();
				
			
			((ObjectNode) bObject).put("status", "sent");
			((ObjectNode) bObject).put("emailSubject", "Loan Application");
			((ObjectNode) bObject).put("templateId", docusigntID);
			((ObjectNode) bObject).put("accountId", docusignaccID);
			
			
			respdata=bObject.toString().replace("\\\\", "");
			logger.info(respdata);
			return respdata;
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respdata;
		
		
		
	}
	

	
	@SuppressWarnings("unused")
	private String jsonFormat(String envelopeData) {
		// TODO Auto-generated method stub
		
		JsonNode bObject,jsArray,jArray;
		String respdata = null;
		
		
		JSONArray array = new JSONArray();
		JSONObject oNode=new JSONObject();
		JSONObject tNode=new JSONObject();
		JSONObject arrObject=new JSONObject();
		new JSONObject();
				try {
			JsonNode mainObject = mapper.readTree(envelopeData);
			bObject=mainObject.get("docusign");
			
			
			jsArray=bObject.get("templateRoles");
			int x=jsArray.size();
			
			for (int i=x-1; i>=0; i--){
				String email=jsArray.get(i).get("email").toString().replace("\"", "");
				String name=jsArray.get(i).get("name").toString().replace("\"", "");
				jArray=bObject.get("templateRoles").get(i);
				logger.info(jArray.toString());
				//jArray = mapper.readTree(jsArray.toString());
				//((ArrayNode) jArray).add("tabs");				
				//jArray=jsArray.get("tabs");
				//((ArrayNode) jsArray).add("textTabs");	
				oNode.put("tabLabel", "Borrower Name");
				oNode.put("value", name);
				tNode.put("tabLabel", "Email ID");
				tNode.put("value", email);
				array.put(oNode);
				array.put(tNode);
			
				
				
				arrObject.put("textTabs", array.toString().replace("\\", ""));
				logger.info(arrObject.toString());
				//aObject.put("tabs", arrObject.toString());
//				jArray=bObject.get("templateRoles").get(i);
				((ObjectNode) jArray).put("tabs", arrObject.toString().replace("\\\\", ""));
				logger.info(jArray.toString().replace("\\\\", ""));
			}
			
			((ObjectNode) bObject).put("status", "sent");
			((ObjectNode) bObject).put("emailSubject", "Loan Application");
			((ObjectNode) bObject).put("templateId", docusigntID);
			((ObjectNode) bObject).put("accountId", docusignaccID);
			
			
			respdata=bObject.toString().replace("\\\\", "");
			logger.info(respdata);
			
			
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				return respdata;
		
		
	}

	public ResponseEntity<String> getAPICall(String url){
		
		String headerValue = "{\"Username\":\"" +  docusignUsername + "\",\"Password\":\"" +  docusignPassword + "\",\"IntegratorKey\":\"" +  docusignikey + "\"}";
		HttpHeaders headers=new HttpHeaders();
    	headers.add("X-DocuSign-Authentication", headerValue);
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	headers.add("Accept","application/json");
    	HttpEntity<String>request = new HttpEntity<String>(headers);
    	logger.info(url);
    	ClientHttpRequestFactory requestFactory = new     
			      HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
		
		rt.setRequestFactory(requestFactory);    	
    	ResponseEntity<String> resp=rt.exchange(url, HttpMethod.GET, request, String.class);
    	logger.info(resp.toString());
		return resp;		
	}
	
public ResponseEntity<String> getAPICall(String url, String envelopeDatas){
		
		String headerValue = "{\"Username\":\"" +  docusignUsername + "\",\"Password\":\"" +  docusignPassword + "\",\"IntegratorKey\":\"" +  docusignikey + "\"}";
		//String envelopeDatas="";
		
		HttpHeaders headers=new HttpHeaders();
    	headers.add("X-DocuSign-Authentication", headerValue);
    	headers.add("Accept", "application/json");
    	headers.setContentType(MediaType.APPLICATION_JSON);
    	HttpEntity<String> request = new HttpEntity<String>(envelopeDatas, headers);
    	ResponseEntity<String> resp=null;
    	ClientHttpRequestFactory requestFactory = new     
			      HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());
		
		rt.setRequestFactory(requestFactory);
		logger.info(url);
		logger.info(envelopeDatas);
    	resp=rt.exchange(url, HttpMethod.POST, request, String.class);
		return resp;		
	}


public String signEmbed(String recipientUrl) {
	// TODO Auto-generated method stub
	//String recipientUrl="https://demo.docusign.net/restapi/v2/accounts/3752085/envelopes/e9313c90-1e56-4484-b578-25f5c146f6a9/views/recipient";
	String receipientData="{\t\t\"authenticationMethod\": \"email\",\n         \"clientUserId\":\"1000\",\n         \"email\":\"sanrad@mailinator.com\",\n         \"returnUrl\":\"https://gateway.murabahaclub.com/dev/ui?events=\",\n         \"userName\":\"Santhosh Radhakrishnan\"}";
	
	String respUrl=getAPICall(recipientUrl,receipientData).getBody().toString();
	return respUrl;
}









}
