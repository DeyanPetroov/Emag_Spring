package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.emag.model.Category;
import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.CategoryDAO;
import com.emag.model.dao.ProductDAO;
import com.emag.model.dao.UserDAO;

@Controller
public class AdminController {

	@Autowired
	private CategoryDAO categoryDAO;	
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProductPage(Model m, HttpSession session){
		
		
		if(session.getAttribute("user") == null) {
			m.addAttribute("invalidSession", "Please log in to view this page.");
			return "products";
		}	

		try {
			if(!this.userDAO.isAdmin((User) session.getAttribute("user"))){
				m.addAttribute("invalidSession", "You do not have the authority to view this page. Sorry!");
				return "index";
			}
		} catch (SQLException e1) {
			return "errorPage";
		}
		
		ArrayList<Category> categories=null;
		try {
			categories = this.categoryDAO.getAllCategories();
		} catch (Exception e) {
			return ("errorPage");
		}
		m.addAttribute("categories", categories);
		return("addProduct");
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.POST)
    public String addProduct(HttpServletRequest request, HttpServletResponse response, HttpSession session, Model m) {
		
		try {
			if(!this.userDAO.isAdmin((User) session.getAttribute("user"))){
				m.addAttribute("invalidSession", "You do not have the authority to view this page. Sorry!");
				return "index";
			}
		} catch (SQLException e1) {
			return "errorPage";
		}
		
		if(session.getAttribute("user") == null) {
			m.addAttribute("invalidSession", "Please log in to view this page.");
			return "products";
		}	
		
		String brand = request.getParameter("brand");
        String model = request.getParameter("model");
        String description = request.getParameter("description");
        String productImageURL = request.getParameter("productImageURL");
        double price = Double.valueOf(request.getParameter("price"));
        boolean availability = Boolean.parseBoolean(request.getParameter("availability"));        
        Product product = null;               
        
		try {
			int category_id = this.categoryDAO.getCategoryID(request.getParameter("categoryName"));
			product = new Product(category_id, brand, model, description, productImageURL, price, availability, 0,
					null);
			this.productDAO.addProduct(product);
			System.out.println("after adding product to db");
			product.setProductID(this.productDAO.getProductId(product)); // takes the ID from the DB and sets it.
			System.out.println("after getting product id from db");

		}
        catch(Exception e) {
        	return ("errorPage");
        }        
                
        //TODO: do something about the empty url
        return "forward:/viewProduct/" + product.getProductID();
    }
	
	
	@RequestMapping(value = "/viewProduct/{productId}", method = RequestMethod.GET)
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
	@RequestMapping(value = "adminPage", method = RequestMethod.GET)
	public String viewAdminPage() {
		return "adminPage";
	}
}
