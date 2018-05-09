package com.emag.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Cart;
import com.emag.model.Product;
import com.emag.model.dao.ProductDAO;

@Controller
public class CartController {

	@Autowired
	private ProductDAO productDAO;

	@RequestMapping(value = {"/addToCart", "/", "/removeFromCart", "/", "cart"}, method = RequestMethod.GET)
	public String viewCart(HttpSession session, Model model) {
		Cart cart = (Cart) session.getAttribute("cart");
		model.addAttribute("cart", cart);
		return "cart";
	}

	@RequestMapping(value = "/removeFromCart", method = RequestMethod.POST)
	public String removeFromCart(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to add items to your cart.");
			return "redirect:/login";
		}

		Long productID = Long.valueOf(request.getParameter("productID"));
		Cart cart = (Cart) session.getAttribute("cart");
		Integer quantity = Integer.valueOf(request.getParameter("quantity"));

		try {
			Product product = productDAO.getProductById(productID);
			cart.removeFromCart(product, quantity);
			model.addAttribute("cart", cart);
			return "cart";
		} catch (SQLException e) {
			return "errorPage";
		}
	}

	@RequestMapping(value = "/addToCart", method = RequestMethod.POST)
	public String addToCart(Model model, HttpServletRequest request, HttpSession session) {
		if (session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to add items to your cart.");
			return "redirect:/login";
		}
		Long productID = Long.valueOf(request.getParameter("productID"));
		Cart cart = (Cart) session.getAttribute("cart");
		Integer quantity = Integer.valueOf(request.getParameter("quantity"));

		try {
			Product product = productDAO.getProductById(productID);
			if (!cart.addToCart(product, quantity)) {
				model.addAttribute("unavailable", "Sorry the product is not in stock.");
				return "products";
			} 
			else {
				model.addAttribute("cart", cart);
				return "cart";
			}
		} catch (SQLException e) {
			return "errorPage";
		}
	}
}
