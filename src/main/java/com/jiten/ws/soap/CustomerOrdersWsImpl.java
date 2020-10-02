package com.jiten.ws.soap;

import com.jiten.ws.trainings.CreateOrdersRequest;
import com.jiten.ws.trainings.CreateOrdersResponse;
import com.jiten.ws.trainings.CustomerOrdersPortType;
import com.jiten.ws.trainings.DeleteOrdersRequest;
import com.jiten.ws.trainings.DeleteOrdersResponse;
import com.jiten.ws.trainings.GetOrdersRequest;
import com.jiten.ws.trainings.GetOrdersResponse;
import com.jiten.ws.trainings.Order;
import com.jiten.ws.trainings.Product;

import java.util.List;
import java.util.Map;


import java.util.ArrayList;
import java.util.HashMap;
import java.math.BigInteger;

import org.apache.cxf.feature.Features;


//@Features(features="org.apache.cxf.feature.LoggingFeature")
public class CustomerOrdersWsImpl implements CustomerOrdersPortType {

	
	private Map<BigInteger, List<Order>> customerOrders = new HashMap<BigInteger, List<Order>>();
	private int currentCustId;
	
	public CustomerOrdersWsImpl() {
		init();
	}
		
	private void init() {
		Product product = new Product();
		product.setId("1");
		product.setDescription("IPhone");
		product.setQuantity(BigInteger.valueOf(3));
		
		Order order= new Order(); 
		order.setId(BigInteger.valueOf(11)); 
		order.setProduct(product); 
		
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		customerOrders.put(BigInteger.valueOf(++ currentCustId), orders);
	}
	
	@Override
	public GetOrdersResponse getOrders(GetOrdersRequest request) {
		BigInteger custId = request.getCustomerId();
		List<Order> orders = customerOrders.get(custId);
		
		GetOrdersResponse response = new GetOrdersResponse();
		response.getOrder().addAll(orders); 
		return response;
	}

	@Override
	public CreateOrdersResponse createOrders(CreateOrdersRequest request) {
		BigInteger customerId = request.getCustomerId();
		Order order = request.getOrder();
		
		if (null == customerOrders.get(customerId)) {
			List<Order> orders = new ArrayList<>();
			orders.add(order);
			customerOrders.put(customerId, orders);
		}
		else {
			List<Order> orders = customerOrders.get(customerId);
			orders.add(order);
		}
		CreateOrdersResponse response = new CreateOrdersResponse();
		response.setResult(true);
		
		
		return response;
	}

	@Override
	public DeleteOrdersResponse deleteOrders(DeleteOrdersRequest request) {
		BigInteger customerId = request.getCustomerId();
		DeleteOrdersResponse deleteOrdersResponse = new DeleteOrdersResponse();
		if(null == customerOrders.get(customerId)) {
			deleteOrdersResponse.setResult(false);
		}
		else {
			customerOrders.remove(customerId);
			deleteOrdersResponse.setResult(true);
		}
		
		return deleteOrdersResponse;
	}

}
