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
        logger.info("Processing recharge request for MSISDN: {} with amount: {}", 
                    request.getMobile(), request.getAmount());
        try {
            RechargeResponse response = adapter.recharge(request);
            logger.info("Recharge processed successfully - Status: {}, Message: {}", 
                        response.getStatus(), response.getMessage());
            return response;
        } catch (Exception e) {
            logger.error("Error processing recharge", e);
            throw e;
        }
    }
}