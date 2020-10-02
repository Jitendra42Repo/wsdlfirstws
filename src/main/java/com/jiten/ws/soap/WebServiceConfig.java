package com.jiten.ws.soap;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Binding;
import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.soap.SOAPBinding;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
// Tells Spring that this particular Bean is a configuration class. 
public class WebServiceConfig {
	
	@Autowired
	Bus bus;
	
	@Bean
	public Endpoint endPoint() {
		Endpoint endPoint = new EndpointImpl(bus, new CustomerOrdersWsImpl());
		endPoint.publish("/customerOrdersService");
		
		SOAPBinding binding = (SOAPBinding)endPoint.getBinding();
		
		List<Handler> handlerChain = new ArrayList<>();
		handlerChain.add(new JaxWsSoapHandler());
		
		binding.setHandlerChain(handlerChain);
		
		
		return endPoint;
		
	}
	
	
	

}
