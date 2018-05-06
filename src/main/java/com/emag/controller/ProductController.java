package com.emag.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Category;
import com.emag.model.Characteristic;
import com.emag.model.Product;
import com.emag.model.dao.CategoryDAO;
import com.emag.model.dao.CharacteristicDAO;
import com.emag.model.dao.ProductDAO;

@Controller
public class ProductController {

	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CharacteristicDAO characteristicDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	
	//get products by category
	@RequestMapping(value = "/category/{category_id}", method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("category_id") Integer category_id, HttpSession session, HttpServletRequest request){
		try {
			System.out.println("category id: " + category_id);
			List<Product> productsByCategory = productDAO.getProductsByCategory(category_id);
			model.addAttribute("products", productsByCategory);
			return "products";
		} 
		catch (SQLException e) {
			return "errorPage";
		}
	}
	
	@RequestMapping(value = "/viewProduct/{product_id}", method = RequestMethod.GET)
	public String product(Model model, @PathVariable("product_id") Long productID) {
		Product product = null;	
		try {
			product = productDAO.getProductById(productID);
			Category category = categoryDAO.getCategoryByName(product.getCategory().getCategoryName());
			List<Characteristic> characteristics = characteristicDAO.allCategoryCharacteristics(category.getCategoryID());
			model.addAttribute("characteristics", characteristics);
			model.addAttribute("product", product);
			return "viewProduct";
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
