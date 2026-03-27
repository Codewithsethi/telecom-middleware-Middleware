package com.telecom.soap;

import com.telecom.soap.request.RechargeSoapRequest;
import com.telecom.soap.response.RechargeSoapResponse;
import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.telecom.soap package.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes.
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RechargeSoapRequest }
     */
    public RechargeSoapRequest createRechargeSoapRequest() {
        return new RechargeSoapRequest();
    }

    /**
     * Create an instance of {@link RechargeSoapResponse }
     */
    public RechargeSoapResponse createRechargeSoapResponse() {
        return new RechargeSoapResponse();
    }
}
