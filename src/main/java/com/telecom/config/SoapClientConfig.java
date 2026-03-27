package com.telecom.config;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

@Configuration
public class SoapClientConfig {

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.telecom.soap");
        return marshaller;
    }

    @Bean
    public WebServiceTemplate webServiceTemplate(Jaxb2Marshaller marshaller) {
        WebServiceTemplate template = new WebServiceTemplate();
        template.setMarshaller(marshaller);
        template.setUnmarshaller(marshaller);

        ClientInterceptor[] interceptors = new ClientInterceptor[]{soapLoggingInterceptor()};
        template.setInterceptors(interceptors);

        return template;
    }

    @Bean
    public ClientInterceptor soapLoggingInterceptor() {
        return new ClientInterceptor() {

            @Override
            public boolean handleRequest(MessageContext messageContext) {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    messageContext.getRequest().writeTo(outputStream);

                    String soapRequest = outputStream.toString("UTF-8");
                    org.apache.logging.log4j.LogManager
                            .getLogger("SOAP")
                            .info("Outgoing SOAP Request:\n{}", soapRequest);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean handleResponse(MessageContext messageContext) {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    messageContext.getResponse().writeTo(outputStream);

                    String soapResponse = outputStream.toString("UTF-8");
                    org.apache.logging.log4j.LogManager
                            .getLogger("SOAP")
                            .info("Incoming SOAP Response:\n{}", soapResponse);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public boolean handleFault(MessageContext messageContext) {
                try {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    messageContext.getResponse().writeTo(outputStream);

                    String soapFault = outputStream.toString("UTF-8");
                    org.apache.logging.log4j.LogManager
                            .getLogger("SOAP")
                            .error("SOAP Fault:\n{}", soapFault);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return true;
            }

            @Override
            public void afterCompletion(MessageContext messageContext, Exception ex) {
                // Optional logging
            }
        };
    }
}