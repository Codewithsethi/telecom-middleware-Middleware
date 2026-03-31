package com.telecom.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telecom.dto.RechargeRequest;
import com.telecom.dto.RechargeResponse;
import com.telecom.service.RechargeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/recharge")
// Allow local frontend and (for containerized flow) any origin while in dev
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:80", "*"})
public class RechargeController {

    private static final Logger logger = LogManager.getLogger(RechargeController.class);
    private final RechargeService service;

    public RechargeController(RechargeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<RechargeResponse> recharge(@Valid @RequestBody RechargeRequest request) {
        logger.info("Transaction Started: Received recharge request for MSISDN: {} with amount: {}", 
                    request.getMobile(), request.getAmount());
        logger.info("Step 1: Request validation completed");
        
        try {
            RechargeResponse response = service.processRecharge(request);
            logger.info("Transaction Completed: Response sent to client");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Transaction Failed: Unable to process recharge request", e);
            throw e;
        }
    }
}