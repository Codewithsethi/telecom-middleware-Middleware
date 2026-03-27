package com.telecom.soap.response;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RechargeSoapResponse", namespace = "http://telecom.com/recharge")
@XmlAccessorType(XmlAccessType.FIELD)
public class RechargeSoapResponse {
    public RechargeSoapResponse() {
    }

    @XmlElement
    private String status;
    @XmlElement
    private String message;

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    @Override
    public String toString() {
        return "RechargeSoapResponse{status='" + status + "', message='" + message + "'}";
    }

    // getters & setters
    
}

