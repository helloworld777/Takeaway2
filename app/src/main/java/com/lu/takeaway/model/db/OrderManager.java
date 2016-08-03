package com.lu.takeaway.model.db;

import java.util.ArrayList;
import java.util.List;

import bean.OrderBean;


public class OrderManager {
	private List<OrderBean> orders;
	private OrderBean orderBean;
	private static OrderManager orderManager;
	private OrderManager(){
		orders=new ArrayList<OrderBean>();
	}
	public static OrderManager getDefault(){
		if(orderManager==null){
			orderManager=new OrderManager();
		}
		return orderManager;
	}
	public List<OrderBean> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderBean> orders) {
		this.orders = orders;
	}
	public OrderBean getOrderBean() {
		return orderBean;
	}
	public void setOrderBean(OrderBean orderBean) {
		this.orderBean = orderBean;
	}
}
