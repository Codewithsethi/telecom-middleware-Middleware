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
        
        logger.info("Calling SOAP Recharge service for MSISDN: {} with amount: {}", 
                    request.getMsisdn(), request.getAmount());
        logger.debug("Full SOAP request: {}", request);

        //"http://soapbackend:8090"//http://localhost:8090/ws
        try {
            // 🔥 THIS LINE CONNECTS MIDDLEWARE TO BACKEND 🔥
            RechargeSoapResponse response = (RechargeSoapResponse)
                    webServiceTemplate.marshalSendAndReceive(
                            "http://soapbackend:8090/ws",
                            request
                    );
            
            logger.info("SOAP Recharge response received - Status: {}, Message: {}", 
                        response.getStatus(), response.getMessage());
            logger.debug("Full SOAP response: {}", response);
            return response;
            
        } catch (Exception e) {
            logger.error("Error calling SOAP Recharge service", e);
            throw e;
        }
    }
}
