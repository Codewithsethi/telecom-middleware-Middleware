package com.telecom.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.telecom.adapter.RechargeAdapter;
import com.telecom.dto.RechargeRequest;
import com.telecom.dto.RechargeResponse;

@Service
public class RechargeService {

    private static final Logger logger = LogManager.getLogger(RechargeService.class);
    private final RechargeAdapter adapter;

    public RechargeService(RechargeAdapter adapter) {
        this.adapter = adapter;
    }

    public RechargeResponse processRecharge(RechargeRequest request) {
        logger.info("Transaction Started: Processing recharge for MSISDN: {} with amount: {}", 
                    request.getMobile(), request.getAmount());
        logger.info("Step 1: Validating request parameters");
        
        try {
            logger.info("Step 2: Converting REST request to SOAP format");
            RechargeResponse response = adapter.recharge(request);
            
            logger.info("Step 3: Processing backend response");
            logger.info("Transaction Completed Successfully - Status: {}, Message: {}", 
                        response.getStatus(), response.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("Transaction Failed: Backend calling failed - {}", e.getMessage());
            logger.error("Step 3: Error occurred during backend communication", e);
            throw e;
        }
    }
}