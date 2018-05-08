package com.emag.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Order {
	
	private long orderID; 
	private LocalDateTime date;
	private User user;
	private double totalCost;
	private int status = 1;
	private String statusDescription;
	private String deliveryAddress;
	private Map<Product, Integer> products;
	
	public Order(long orderID, LocalDateTime date, User user, double totalCost, int status) {
		this.orderID = orderID;
		this.date = date;
		this.user = user;
		this.totalCost = totalCost;
		this.status = status;
		setStatusDescription();
	}

	public Order(long orderID, User user, String deliveryAddress, LocalDateTime date, double totalCost, Map<Product, Integer> products) {
		this(user, deliveryAddress, totalCost, products);
		this.orderID = orderID;
		this.status = 2;
		setStatusDescription();
		this.date = date;
	}
	
	public Order(User user, String deliveryAddress, double totalCost, Map<Product, Integer> products) {
		this.user = user;
		this.date = LocalDateTime.now();
		this.status = 2;
		this.products = products;
		this.deliveryAddress = deliveryAddress;
		this.totalCost = totalCost;
		setStatusDescription();
	}

	//==================GETTERS==================

	public LocalDateTime getDate() {
		return this.date;
	}

	public User getUser() {
		return user;
	}

	public double getTotalCost() {
		return this.totalCost;
	}

	public int getStatus() {
		return status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}


	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(this.products);
	}
	
	public long getOrderID() {
		return orderID;
	}
	
	//==================SETTERS==================
	
	public void setStatusDescription() {
		switch (this.status) {
		case 1:
			this.statusDescription = "No order present.";
			break;
		case 2:
			this.statusDescription = "Order is awaiting confirmation";
			break;
		case 3:
			this.statusDescription = "Order has been confirmed and has been shipped.";
			break;
		case 4:
			this.statusDescription = "Order has been delivered";
			break;
		default:
			this.statusDescription = "Invalid status";
			break;
		}
	}

	public void setTotalCost(double totalCost) {
		if (totalCost >= 0) {
			this.totalCost = totalCost;
		}
	}
	
	public void setProducts(Map<Product, Integer> products) {
		this.products = products;
	}
	
	public void setOrderID(long orderID) {
		this.orderID = orderID;
	}
	
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (orderID ^ (orderID >>> 32));
		long temp;
		temp = Double.doubleToLongBits(totalCost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderID != other.orderID)
			return false;
		if (Double.doubleToLongBits(totalCost) != Double.doubleToLongBits(other.totalCost))
			return false;
		return true;
	}
	
	
}