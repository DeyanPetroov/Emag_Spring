package com.emag.model.dao;

import java.util.List;

import com.emag.model.*;

public interface IOrderDAO {
	
	void addNewOrder(Order order) throws Exception;
	void removeOrder(long orderID) throws Exception;
	Order getOrderByID(long orderID) throws Exception;
	void updateOrderStatus(User user, int statusID, long orderID) throws Exception;
	User getUserByOrderID(long userID) throws Exception;	
	void addOrderedProduct(Order order) throws Exception;
	List<Order> getAllUserOrders(long userID) throws Exception;
	void deleteOrderedProduct(long productID) throws Exception;
}
