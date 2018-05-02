package com.emag.model.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Component;

import com.emag.model.*;

@Component
public class OrderDAO implements IOrderDAO {
	
	private static final String UPDATE_ORDER_STATUS_FOR_USER = "UPDATE orders SET status_id = ? WHERE user_id = ?";
	private static final String DELETE_ORDER_BY_ID = "DELETE FROM orders WHERE order_id = ?";
	private static final String NEW_ORDER = "INSERT INTO orders (date, total_cost, delivery_address, user_id, status_id) VALUES (?,?,?,?,?)"; 
	private static final String GET_ORDER_BY_ID = 
			"SELECT o.order_id, o.date, o.total_cost, o.delivery_address, CONCAT(u.first_name, ' ', u.last_name) AS Name, s.status_description" + 
			"FROM orders AS o" +  
			"JOIN statuses AS s" + 
			"ON s.status_id = o.status_id" + 
			"JOIN users AS u" + 
			"ON o.user_id = u.user_id AND o.order_id  = ?";
	private static final String GET_USER_BY_ORDER_ID = 
			"SELECT u.username, u.password, u.first_name, u.last_name, u.email, u.age " + 
			"FROM users as u" + 
			"JOIN orders as o" + 
			"WHERE o.order_id = ?";
	private static final String INSERT_ORDERED_PRODUCT = "INSERT INTO ordered_products(order_id, product_id, quantity) VALUES(?, ?, ?)";
	
	private Connection connection;
	
	private OrderDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addNewOrder(Order order) throws SQLException {
		try(PreparedStatement insertOrder = connection.prepareStatement(NEW_ORDER);){
			insertOrder.setObject(1, order.getDate());
			insertOrder.setDouble(2, order.getTotalCost());
			insertOrder.setString(3, order.getDeliveryAddress());
			insertOrder.setLong(4, order.getUser().getId());
			insertOrder.setInt(5, order.getStatus());
			insertOrder.executeUpdate();
		}
	}

	@Override
	public Order getOrderById(long user_id) throws SQLException {
		Order order = null;
		try (PreparedStatement getOrderById = connection.prepareStatement(GET_ORDER_BY_ID);) {
			getOrderById.setLong(1, user_id);
			try (ResultSet resultSet = getOrderById.executeQuery()) {
				while (resultSet.next()) {
					User user = getUserByOrderId(user_id);
					order = new Order(user, resultSet.getString("delivery_address"));
				}
			}
		}
		
		return order;
	}

	@Override
	public void removeOrder(long order_id) throws SQLException {
		try(PreparedStatement removeOrder = connection.prepareStatement(DELETE_ORDER_BY_ID);){
			removeOrder.setLong(1, order_id);
			removeOrder.executeUpdate();
		}
	}

	@Override
	public void updateOrderStatus(User user, int status_id) throws SQLException {
		try (PreparedStatement updateOrder = connection.prepareStatement(UPDATE_ORDER_STATUS_FOR_USER);) {
			updateOrder.setInt(1, status_id);
			updateOrder.setLong(2, user.getId());
			updateOrder.executeUpdate();
		}
	}
	
	@Override
	public User getUserByOrderId(long order_id) throws SQLException {
		User user = null;
		try (PreparedStatement getUser = connection.prepareStatement(GET_USER_BY_ORDER_ID);) {
			getUser.setLong(1, order_id);
			try (ResultSet result = getUser.executeQuery();) {

				while (result.next()) {
					user = new User(
							result.getString("username"), 
							result.getString("column"),
							result.getString("first_name"), 
							result.getString("last_name"), 
							result.getString("email"),
							result.getInt("age"));
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
}