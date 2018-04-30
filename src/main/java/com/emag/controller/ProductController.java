package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Product;
import com.emag.model.dao.ProductDAO;

@Controller
public class ProductController {

	@Autowired
	private ProductDAO productDAO;
	
	//get products by category
	@RequestMapping(value = "/category/{category_id}", method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("category_id") Integer category_id, HttpServletRequest request){
		try {
			ArrayList<Product> productsByCategory = (ArrayList<Product>) productDAO.getProductsByCategory(category_id);
			model.addAttribute("products", productsByCategory);
			return "products";
		} 
		catch (SQLException e) {
			return "errorPage";
		}
	}
}
