package com.murabahaclub.DocuSign.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.murabahaclub.DocuSign.Service.DocuSignService;


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
	
	//POST Requests
	
		/**
	     * This api is used to Creating Envelope for getting eSign using POST method
	     * @RequestBody 
	     * 
	     * 
	     */

		@RequestMapping(value="/CreateEnvelope", method = RequestMethod.POST)
		@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
		public ResponseEntity<String> createEnvelope(@RequestBody String envelopeData)
		{
			ResponseEntity<String> envelopeEntity=docuSignService.createEnvelope(envelopeData);
			return envelopeEntity;
			
		}
		
		/*@RequestMapping(value="/Login", method = RequestMethod.POST)
		@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", required = true, dataType = "string", paramType = "header")})
		public ResponseEntity<String> loginDetails(@RequestBody String envelopeData)
		{
			ResponseEntity<String> envelopeEntity=docuSignService(envelopeData);
			return envelopeEntity;
			
		}*/
	

}
