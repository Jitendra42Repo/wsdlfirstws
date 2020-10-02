package com.jiten.ws.soap;

import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.Node;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/*
 * This class acts like Interceptor during Cross-cutting Concern. 
 * 			E.g. Versioning, Cache, Authentication 
 * 
 * Flow: getHeaders() --> handleMessage()
 * 						getHeaders() <---- handleMessage()  <--- close()
 */
public class JaxWsSoapHandler implements SOAPHandler<SOAPMessageContext> {

	/*
	 * Invoked during request and response. 
	 */
	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		
		System.out.println("Entering the handleMessage method... ");
		Boolean isRequest = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		System.out.println("MESSAGE_OUTBOUND_PROPERTY value ===> " + isRequest);
		
		if (!isRequest) {
			SOAPMessage message = context.getMessage();
			SOAPPart soapPart = message.getSOAPPart();
			try {
				soapPart.getEnvelope();
				SOAPEnvelope envelope = soapPart.getEnvelope();
				SOAPHeader header = envelope.getHeader();
				Iterator childElements = header.getChildElements();
				
				while(childElements.hasNext()) {
					Node child = (Node) childElements.next();
					String localName = child.getLocalName();
					if(localName != null && localName.equals("SiteName")) {
						System.out.println("Request from Site ===>" + child.getValue());
					}
				}
			} catch (SOAPException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else {
			System.out.println("From handleMessage method: Response message is dispatching... ");
		}
		return true;
	}

	/*
	 * Invoked when the exception happens.
	 */
	@Override
	public boolean handleFault(SOAPMessageContext context) {
		System.out.println("Entering the handleFault method... ");
		return false;
	}
	/*
	 * Invoked when the response is about to be dispatched. 
	 * 	Can be used to free the resources. Such as, Database.
	 */
	@Override
	public void close(MessageContext context) {
		System.out.println("Entering the close method... ");
	}
	/*
	 * Relates to "mustUnderstand" attribute. Has two values "1" and "0". If any element has this attribute
	 * 		with value equal to "1" then it must be handled within this method else, SOAP will return 
	 * 			"Fault" messages/response.
	 */
	@Override
	public Set<QName> getHeaders() {
		System.out.println("Entering the getHeaders method... ");
		return null;
	}

}
