package com.emag.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

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
import com.emag.model.dao.ProductDAO;

@Controller
public class OrderController {
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private ProductDAO productDAO;
	
	private Connection connection  = DBManager.getInstance().getConnection();

	@RequestMapping(value = "/orderPage", method = RequestMethod.GET)
	public String orderProducts(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to view this page.");	
			return "redirect:/login";
		}	
		return "orderPage";
	}
	
	@RequestMapping(value = "/finalizeOrder", method = RequestMethod.GET)
	public String finalize(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in.");	
			return "redirect:/login";
		}	
		return "index";
	}
	 
	@RequestMapping(value = "/finalizeOrder", method = RequestMethod.POST)
	public String finalizeOrder(Model model, HttpSession session, HttpServletRequest request) throws SQLException {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in.");	
			return "redirect:/login";
		}	
		User user = (User) session.getAttribute("user");
		String deliveryAddress = request.getParameter("address");

		Order order = new Order(user, deliveryAddress, user.getCart().getTotalCost(), user.getCart().getProducts());
		
		if(user.getCart().getTotalCost() == 0) {
			model.addAttribute("invalidOrder");	
			return "redirect:/index";
		}
		
		try {
			connection.setAutoCommit(false);
			orderDAO.addNewOrder(order);
			orderDAO.addOrderedProduct(order);
			for(Entry<Product, Integer> orderedProducts : user.getCart().getProducts().entrySet()) {
				Product product = orderedProducts.getKey();
				product.setAvailability(product.getAvailability() - orderedProducts.getValue());
				productDAO.updateProduct(product);
			}
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw new SQLException("The transaction is not completed. The reason is: " + e.getMessage());
		} finally {
			connection.setAutoCommit(true);
		}

		model.addAttribute("order", order);
		user.addToHistory(order);
		user.getCart().emptyCart();
		return "viewOrder";
	}
	
	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public String viewHistory(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in.");	
			return "redirect:/login";
		}	
		User user = (User) session.getAttribute("user");
		if(user == null) {
			model.addAttribute("invalidSession", "Please log in.");	
			return "redirect:/login";
		}
		try {
			//get orders for this user
			List<Order> orders = orderDAO.getAllUserOrders(user.getID());
			//get products for order
			Map<Order, Map<Product, Integer>> products = new TreeMap<Order, Map<Product,Integer>>(new Comparator<Order>() {
				@Override
				public int compare(Order o1, Order o2) {
					if(o1.getDate().isBefore(o2.getDate())) {
						return -1;
					}
					else if(o1.getDate().isAfter(o2.getDate())) {
						return 1;
					}
					return o1.getDate().compareTo(o2.getDate());
				}		
			});
			for(Order order: orders) {
				Map<Product, Integer> p = productDAO.orderProducts(order.getOrderID());
				products.put(order, p);
			}
			
			model.addAttribute("orders", orders);
			model.addAttribute("products", products);
		} catch (SQLException e) {
			return "errorPage";
		}
		return "orderHistory";
	}
}