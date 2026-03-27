package com.telecom.soap.request;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RechargeSoapRequest", namespace = "http://telecom.com/recharge")
@XmlAccessorType(XmlAccessType.FIELD)
public class RechargeSoapRequest {
    public RechargeSoapRequest() {
    }

    @XmlElement
    private String msisdn;
    @XmlElement
    private double amount;

	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}

    @Override
    public String toString() {
        return "RechargeSoapRequest{msisdn='" + msisdn + "', amount=" + amount + "}";
    }

    // getters & setters
    
}

