package com.murabahaclub.DocuSign.Controller;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.murabahaclub.DocuSign.Service.DocuSignService;
import com.ziggeo.Ziggeo;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

/**
 * 
 * @author sradhakrishnan2
 *
 */

@RestController
@RequestMapping("/docusign")
@CrossOrigin
public class DocuSignController {
	
	@Autowired
	private DocuSignService docuSignService;
	
	@Value("${Ziggeo.token}")
	private String zigToken;
	
	@Value("${Ziggeo.privateKey}")
	private String zigKey;
	
	@Value("${Ziggeo.encryt}")
	private String zigEncryt;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	Ziggeo zig =new Ziggeo(zigToken, zigKey, zigEncryt);
	
	//POST Requests
	
		/**
	     * This api is used to Creating Envelope for getting eSign using POST method
	     * @RequestBody 
	     * 
	     * 
	     */

		@RequestMapping(value="/CreateEnvelope", method = RequestMethod.POST)
		@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
		public String createEnvelope(@RequestBody String envelopeData)
		{
			String envelopeEntity=docuSignService.createEnvelope(envelopeData);
			return envelopeEntity;
			
		}
		
		/**
		 * Ziggeo Get Videos
		 * @return
		 * @throws JSONException
		 * @throws IOException
		 */
		
		@RequestMapping(value="/getVideos", method = RequestMethod.GET)
		@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
		public String getVideos() throws JSONException, IOException
		{

				Ziggeo zig =new Ziggeo(zigToken, zigKey, zigEncryt);	
				String token=zig.authtokens().create(new JSONObject("{\"grants\":{\"read\":{\"all\":true}}}")).get("token").toString();
				logger.info("Token {}",token);
				
				token=zig.videos().index(new JSONObject("{\"limit\":\"3\"}")).toString();
				logger.info("Videos {}",token);
				//ObjectMapper mapper=new ObjectMapper();
				//JsonNode nod=mapper.readTree(token);
				//nod.get("original_stream").
				
				String dat=zig.videos().get("r11674dbe0c534352adf375247032a90").toString();
				logger.info(dat);
				
			
			
			return zig.toString();
			
		}
	

}
