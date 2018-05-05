package com.emag.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Cart;
import com.emag.model.Order;
import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.DBManager;
import com.emag.model.dao.OrderDAO;

@Controller
public class OrderController {
	
	@Autowired
	private OrderDAO orderDAO;
	
	private Connection connection  = DBManager.getInstance().getConnection();

	@RequestMapping(value = "/orderPage", method = RequestMethod.GET)
	public String orderProducts(HttpSession session) {
		return "orderPage";
	}
	 
	@RequestMapping(value = "/finalizeOrder", method = RequestMethod.POST)
	public String finalizeOrder(Model model, HttpSession session, HttpServletRequest request) throws SQLException {
		User user = (User) session.getAttribute("user");
		String deliveryAddress = request.getParameter("address");

		Order order = new Order(user, deliveryAddress);
		
		try {
			connection.setAutoCommit(false);
			orderDAO.addNewOrder(order);
			orderDAO.addOrderedProduct(order);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw new SQLException("the transaction is not made" + e.getMessage());
		} finally {
			connection.setAutoCommit(true);
		}

		model.addAttribute("order", order);
		Map<Product, Integer> productss = order.getProducts();
		for(Entry<Product,Integer> entry : productss.entrySet()) {
			System.out.println(entry.getKey().getModel());
			System.out.println(entry.getKey().getPrice());
			System.out.println(entry.getValue());
		}
		user.addToHistory(order);
		user.getCart().emptyCart();
		return "viewOrder";
	}
}