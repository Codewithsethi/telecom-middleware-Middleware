package com.telecom.soap.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.WebServiceTemplate;

import com.telecom.soap.request.RechargeSoapRequest;
import com.telecom.soap.response.RechargeSoapResponse;

@Component
public class RechargeSoapClient {

    private static final Logger logger = LogManager.getLogger(RechargeSoapClient.class);
    private final WebServiceTemplate webServiceTemplate;

    public RechargeSoapClient(WebServiceTemplate webServiceTemplate) {
        this.webServiceTemplate = webServiceTemplate;
    }

    public RechargeSoapResponse callRecharge(RechargeSoapRequest request) {
        
        logger.info("Step 2.1: Preparing SOAP request for backend call");
        logger.debug("SOAP request details - MSISDN: {}, Amount: {}", 
                    request.getMsisdn(), request.getAmount());
        
        logger.info("Step 2.2: Calling SOAP backend at http://soapbackend:8090/ws");
        
        try {
            // 🔥 THIS LINE CONNECTS MIDDLEWARE TO BACKEND 🔥
            RechargeSoapResponse response = (RechargeSoapResponse)
                    webServiceTemplate.marshalSendAndReceive(
                            "http://soapbackend:8090/ws",
                            request
                    );
            
            logger.info("Step 2.3: SOAP response received from backend");
            logger.info("Backend response - Status: {}, Message: {}", 
                        response.getStatus(), response.getMessage());
            return response;
            
        } catch (Exception e) {
            logger.error("Transaction Failed: Backend calling failed - check the URL http://soapbackend:8090/ws");
            logger.error("Step 2.3: Error during SOAP backend communication", e);
            throw e;
        }
    }
}
