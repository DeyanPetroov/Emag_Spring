package com.emag.controller;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Order;
import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.OrderDAO;
import com.emag.model.dao.ProductDAO;
import com.emag.model.dao.UserDAO;


@Controller
public class SearchController {
	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
	public String search(HttpServletRequest request, Model model) {
		
		String search = null;
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
		}
		else{
			search = (String) request.getAttribute("search");
		}
		
		Collection<Product> searchProd;
		try {
			searchProd = this.productDAO.searchProduct(search);
		} catch (SQLException e) {
			return "errorPage";
		}
		
		if(!searchProd.isEmpty()){
			for (Product product : searchProd) {
				System.out.println(product);
			}
			
			model.addAttribute("products", searchProd);
		}
		else{
			model.addAttribute("message", "No products found. ");
		}
		return "products";		
	}
	
	@RequestMapping(value = "searchOrder", method = RequestMethod.GET)
	public String searchOrder(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to view this page.");	
			return "redirect:/login";
		}
		
		try {
			if(!this.userDAO.isAdmin((User) session.getAttribute("user"))){
				model.addAttribute("invalidSession", "You do not have the authority to view this page. Sorry!");
				return "index";
			}
		} 
		catch (SQLException e1) {
			return "errorPage";
		}
		return "searchOrder";
	}

	
	@RequestMapping(value = "searchOrder", method = RequestMethod.POST)
	public String searchOrder(Model model, HttpServletRequest request, HttpSession session) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to view this page.");	
			return "redirect:/login";
		}
		
		try {
			if(!this.userDAO.isAdmin((User) session.getAttribute("user"))){
				model.addAttribute("invalidSession", "You do not have the authority to view this page. Sorry!");
				return "index";
			}
		} 
		catch (SQLException e1) {
			return "errorPage";
		}
		
		Long orderID = Long.valueOf(request.getParameter("search"));
		try {
			Order order = orderDAO.getOrderByID(orderID);
			model.addAttribute("order", order);
			return "changeStatus";
		} catch (SQLException e) {
			return "errorPage";
		}
	}
}
