package com.telecom.adapter.impl;

import org.springframework.stereotype.Component;

import com.telecom.adapter.RechargeAdapter;
import com.telecom.dto.RechargeRequest;
import com.telecom.dto.RechargeResponse;
import com.telecom.soap.client.RechargeSoapClient;
import com.telecom.soap.request.RechargeSoapRequest;
import com.telecom.soap.response.RechargeSoapResponse;

@Component
public class SoapRechargeAdapter implements RechargeAdapter {

    private final RechargeSoapClient soapClient;

    public SoapRechargeAdapter(RechargeSoapClient soapClient) {
        this.soapClient = soapClient;
    }

    @Override
    public RechargeResponse recharge(RechargeRequest request) {

        // REST → SOAP
        RechargeSoapRequest soapRequest = new RechargeSoapRequest();
        soapRequest.setMsisdn(request.getMobile());
        soapRequest.setAmount(request.getAmount());

        // Call SOAP backend
        RechargeSoapResponse soapResponse =
                soapClient.callRecharge(soapRequest);

        // SOAP → REST
        RechargeResponse response = new RechargeResponse();
        response.setStatus(soapResponse.getStatus());
        response.setMessage(soapResponse.getMessage());

        return response;
    }
}
