package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.ProductDAO;
import com.emag.model.dao.UserDAO;

@Controller
public class ProductController {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private UserDAO userDAO;
	
	//get products by category
	@RequestMapping(value = "/category/{category_id}", method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("category_id") Integer category_id, HttpSession session, HttpServletRequest request){
		try {
			List<Product> productsByCategory = productDAO.getProductsByCategory(category_id);
			model.addAttribute("products", productsByCategory);
			request.setAttribute("isAdmin", this.userDAO.isAdmin((User) session.getAttribute("user")));
			return "products";
		} 
		catch (SQLException e) {
			return "errorPage";
		}
	}
	
	//TODO
	//make product page with product characteristics and add to cart and add to favourites buttons
	//every product should have a picture
	//make menu with hot offers
	//deal with JSON
}
