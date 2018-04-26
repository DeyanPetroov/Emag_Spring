package com.emag.controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.emag.model.Category;
import com.emag.model.Product;
import com.emag.model.dao.ProductDAO;

@Controller
public class AdminController {

	@Autowired
	private ProductDAO productDAO;	
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProductPage(Model m){
		ArrayList<Category> categories=null;
		try {
			categories = this.productDAO.getCategoryDAO().getAllCategories();
		} catch (Exception e) {
			return ("errorPage");
		}
		m.addAttribute("categories", categories);
		return("addProduct");
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public ModelAndView addProduct(HttpServletRequest request, HttpServletResponse response) {
        
		int category_id = Integer.valueOf(request.getParameter("category_id"));
		String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String description = request.getParameter("description");
        String productImageURL = request.getParameter("productImageURL");
        double price = Double.valueOf(request.getParameter("price"));
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));        
        //TODO: add category_id or name from form choice -> here to add successfully
        Product product = new Product(category_id, brand, model, description, productImageURL, price, availability, 0, null);
        try {
			this.productDAO.addProduct(product);
        }
        catch(Exception e) {
        	return new ModelAndView("forward:/errorPage/");
        }        
                
        return new ModelAndView("forward:/viewProduct/" + product.getProduct_id());
    }
	
	
	@RequestMapping(value = "/viewProduct", method = RequestMethod.GET)
	public String viewProduct(@PathVariable long productId, Model model){
		Product product;
		try {
			product = this.productDAO.getProductById(productId);
		} catch (SQLException e) {
			return("errorPage");
		}
		model.addAttribute(product);
		
		return("viewProduct");
	}
	
	
	
}
