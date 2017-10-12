package com.murabahaclub.DocuSign.Service;

import org.springframework.http.ResponseEntity;

public interface DocuSignService {
	
	public ResponseEntity<String> createEnvelope(String envelopeData);
	
	
	

}
