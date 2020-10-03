# wsdlfirstws: CustomerOrder SOAP Service

1. Online shopping management and tracing system.
2. Facilitates consumer applications to keep track of list of products and orders of customers.
3. Create order for a product, retrieve and delete list of orders placed. 
4. Valide Site name (consumer-application name) is present in the reqauest.

## Tech Stack:
1. Create Spring starter project using Spring Tool Suite IDE.
2. Spring Boot JAX-WS application build on top of Apache CXF web service stack engine.
3. Implement JAX-WS Handler interface(SOAPHander<SOAPMessageContext>) in order to meet cross-cutting concerns (non-functional requirement like Authentication, Versioning). i.e. Validate SOAP Message request has <SiteName> tag inside <soapenv:header> and print the name.
            * Incoming request Flow: getHeaders() --> handleMessage().
						    * Outgoing request Flow: close() --> handleMessage() --> getHeaders().
4. In memory database stores the customer order details.
5. Test service endpoints using SOAP UI. 
