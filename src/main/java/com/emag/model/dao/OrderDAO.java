package com.emag.model.dao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emag.model.*;
import com.mysql.jdbc.Statement;

@Component
public class OrderDAO implements IOrderDAO {
	
	private static final String UPDATE_ORDER_STATUS_FOR_USER = "UPDATE orders SET status_id = ? WHERE user_id = ? AND order_id = ?";
	private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
	private static final String NEW_ORDER = "INSERT INTO orders (date, total_cost, delivery_address, user_id, status_id) VALUES (?,?,?,?,?)"; 
	private static final String GET_ORDER_BY_ID = 
			"SELECT o.order_id, o.date, o.total_cost, o.delivery_address, CONCAT(u.first_name, ' ', u.last_name) AS Name, s.status_description, s.status_id " + 
			"FROM orders AS o " +  
			"JOIN statuses AS s " + 
			"ON s.status_id = o.status_id " + 
			"JOIN users AS u " + 
			"ON o.user_id = u.user_id AND o.order_id  = ?";
	private static final String GET_USER_BY_ORDER_ID = 
			"SELECT u.user_id, u.username, u.first_name, u.last_name, u.email, u.age " + 
			"FROM users as u " + 
			"JOIN orders as o " + 
			"ON u.user_id = o.user_id " + 
			"AND o.order_id = ?";
	private static final String INSERT_ORDERED_PRODUCT = "INSERT INTO ordered_products(order_id, product_id, quantity) VALUES(?, ?, ?)";
	private static final String GET_ORDERS_FOR_USER = "SELECT order_id, date, total_cost, status_id FROM orders WHERE user_id = ? order by date";
	
	private Connection connection;
	
	private OrderDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public void addNewOrder(Order order) throws SQLException {
		try(PreparedStatement insertOrder = connection.prepareStatement(NEW_ORDER, Statement.RETURN_GENERATED_KEYS)){
			insertOrder.setTimestamp(1, Timestamp.valueOf(order.getDate()));
			insertOrder.setDouble(2, order.getTotalCost());
			insertOrder.setString(3, order.getDeliveryAddress());
			insertOrder.setLong(4, order.getUser().getID());
			insertOrder.setInt(5, order.getStatus());
			insertOrder.executeUpdate();
			
			try(ResultSet result = insertOrder.getGeneratedKeys()){
				if(result.next()) {
					order.setOrderID(result.getLong(1));
				}
			}
		}
	}

	@Override
	public Order getOrderByID(long orderID) throws SQLException {
		Order order = null;
		try (PreparedStatement getOrderById = connection.prepareStatement(GET_ORDER_BY_ID);) {
			getOrderById.setLong(1, orderID);
			try (ResultSet resultSet = getOrderById.executeQuery()) {
				while (resultSet.next()) {
					User user = getUserByOrderID(orderID);
					LocalDateTime date =  LocalDateTime.ofInstant(Instant.ofEpochMilli(resultSet.getTimestamp("date").getTime()), 
                            TimeZone.getDefault().toZoneId()); 
					order = new Order(resultSet.getLong("order_id"), 
							date,
							user, 
							resultSet.getDouble("total_cost"), 
							resultSet.getInt("status_id"));
				}
			}
		}	
		return order;
	}

	@Override
	public void removeOrder(long orderID) throws SQLException {
		try(PreparedStatement removeOrder = connection.prepareStatement(DELETE_ORDER_BY_ID);){
			removeOrder.setLong(1, orderID);
			removeOrder.executeUpdate();
		}
	}

	@Override
	public void updateOrderStatus(User user, int statusID, long orderID) throws SQLException {
		try (PreparedStatement updateOrder = connection.prepareStatement(UPDATE_ORDER_STATUS_FOR_USER);) {
			updateOrder.setInt(1, statusID);
			updateOrder.setLong(2, user.getID());
			updateOrder.setLong(3, orderID);
			updateOrder.executeUpdate();
		}
	}
	
	@Override
	public User getUserByOrderID(long orderID) throws SQLException {
		User user = null;
		try (PreparedStatement getUser = connection.prepareStatement(GET_USER_BY_ORDER_ID);) {
			getUser.setLong(1, orderID);
			try (ResultSet result = getUser.executeQuery();) {
				while (result.next()) {
					user = new User(
							result.getLong("user_id"),
							result.getString("username"),
							result.getString("first_name"), 
							result.getString("last_name"), 
							result.getString("email"));
				}
			}
		}
		return user;
	}

	@Override
	public void addOrderedProduct(Order order) throws SQLException {
		Map<Product, Integer> products = order.getProducts();
		for(Entry<Product, Integer> p : products.entrySet()) {
			try(PreparedStatement ps = connection.prepareStatement(INSERT_ORDERED_PRODUCT);){
				ps.setLong(1, order.getOrderID());
				ps.setLong(2, p.getKey().getProductID());
				ps.setInt(3, p.getValue());
				ps.executeUpdate();
			}
		}
		
	}
	
	@Override
	public List<Order> getAllUserOrders(long userID) throws SQLException {
		List<Order> userOrders = new ArrayList<>();
		try (PreparedStatement getOrders = connection.prepareStatement(GET_ORDERS_FOR_USER);) {
			getOrders.setLong(1, userID);
			try (ResultSet result = getOrders.executeQuery();) {
				while (result.next()) {
					User user = userDAO.getUserByID(userID);
					Map<Product, Integer> products = user.getCart().getProducts();
					LocalDateTime date =  LocalDateTime.ofInstant(Instant.ofEpochMilli(result.getTimestamp("date").getTime()), 
					                                TimeZone.getDefault().toZoneId()); 
					Order order = new Order(result.getInt("order_id"), date, user, result.getDouble("total_cost"), result.getInt("status_id"));
					userOrders.add(order);
				}
			}
		}
		return userOrders;
	}
}