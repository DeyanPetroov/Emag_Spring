package com.emag.controller;

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

	@RequestMapping(value = "/orderPage", method = RequestMethod.GET)
	public String orderProducts(HttpSession session) {
		return "orderPage";
	}
	
	@RequestMapping(value = "/finalizeOrder", method = RequestMethod.POST)
	public String finalizeOrder(Model model, HttpSession session, HttpServletRequest request) {
		User user = (User) session.getAttribute("user");
		String deliveryAddress = request.getParameter("address");

		Order order = new Order(user, deliveryAddress);
		
		try {
			//doesn't work
			//should be transaction
			orderDAO.addNewOrder(order);
			System.out.println("after dao new order");
			orderDAO.addOrderedProduct(order);
			System.out.println("after dao new ordered product");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("order", order);
		System.out.println("===========================");
		Map<Product, Integer> productss = order.getProducts();
		for(Entry<Product,Integer> entry : productss.entrySet()) {
			System.out.println(entry.getKey().getModel());
			System.out.println(entry.getKey().getPrice());
			System.out.println(entry.getValue());
		}
		System.out.println("===========================");
		user.addToHistory(order);
		user.getCart().emptyCart();
		return "viewOrder";
	}
}