package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.emag.model.Category;
import com.emag.model.Characteristic;
import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.CategoryDAO;
import com.emag.model.dao.CharacteristicDAO;
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
	@Autowired
	private CharacteristicDAO characteristicDAO;
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public String addProductPage(Model m, HttpSession session) {
		if(session.getAttribute("user") == null) {
			m.addAttribute("invalidSession", "Please log in to view this page.");	
			return "redirect:/login";
		}	

		try {
			if(!this.userDAO.isAdmin((User) session.getAttribute("user"))){
				m.addAttribute("invalidSession", "You do not have the authority to view this page. Sorry!");
				return "index";
			}
		} 
		catch (SQLException e1) {
			return "errorPage";
		}
		
		List<Category> categories = null;
		List<Characteristic> characteristics = null;
		try {
			categories = this.categoryDAO.getAllCategories();
			characteristics = characteristicDAO.allCharacteristics();
		} catch (Exception e) {
			return ("errorPage");
		}
		
		m.addAttribute("categories", categories);
		m.addAttribute("characteristics", characteristics);
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
			return "redirect:/login";
		}	
		
		String brand = request.getParameter("brand");
        String productModel = request.getParameter("model");
        String description = request.getParameter("description");
        double price = Double.valueOf(request.getParameter("price"));
        Integer availability = Integer.valueOf(request.getParameter("availability"));
        Product product = null;   
        String unit = request.getParameter("unit");
        String value = request.getParameter("value");
        
		try {
			Category category = this.categoryDAO.getCategoryByName(request.getParameter("categoryName"));
			System.out.println("category");
			product = new Product().
					withCategory(category).
					withBrand(brand).
					withDescription(description).
					withModel(productModel).
					withPrice(price).
					withAvailability(availability);
			this.productDAO.addProduct(product);
			m.addAttribute("productId", product.getProductID());
		}
        catch(Exception e) {
        	return ("errorPage");
        }        
                
		m.addAttribute("product", product);
        return "viewProduct";
    }
	
	@RequestMapping(value = "/editProduct/{productID}", method = RequestMethod.GET)
    public String editProduct(HttpSession session, Model m, @PathVariable("productID") Integer productID) {
		
		if(session.getAttribute("user") == null) {
			m.addAttribute("invalidSession", "Please log in to view this page.");
			return "redirect:/login";
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
		List<Characteristic> characteristics = null;
		Product product = null;
		
		try {
			categories = this.categoryDAO.getAllCategories();
			characteristics = characteristicDAO.allCharacteristics();
			product = this.productDAO.getProductById(productID);
		} catch (Exception e) {
			return ("errorPage");
		}
		
		m.addAttribute("categories", categories);
		m.addAttribute("product", product);
		m.addAttribute("characteristics", characteristics);
		
        return "editProduct";
    }
	
	@RequestMapping(value = "/editProduct/{productID}", method = RequestMethod.POST)
    public String editProduct(HttpServletRequest request, @PathVariable("productID") Integer productID, HttpSession session, Model m) throws SQLException {
		
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
			return "redirect:/login";
		}	
		
		Product product = this.productDAO.getProductById(productID);		
		String brand = request.getParameter("brand");
        String productModel = request.getParameter("model");
        String description = request.getParameter("description");        
        double price = Double.valueOf(request.getParameter("price"));
        Integer availability = Integer.valueOf(request.getParameter("availability"));
        int discountPercent = Integer.valueOf(request.getParameter("discountPercent"));
        if(discountPercent!=0 && !this.productDAO.getSaleStatus()) {
        	price -= price * (Double) (0.01*discountPercent);
        }
		
		Product updatedProduct = new Product().
				withProductID(product.getProductID()).
				withCategory(product.getCategory()).
				withBrand(brand).withModel(productModel).
				withDescription(description).
				withPrice(price).
				withAvailability(availability).
				withDiscountPercent(discountPercent).
				withDiscountExpiration(product.getDiscountExpiration());
		this.productDAO.updateProduct(updatedProduct);	
		
		Product p = this.productDAO.getAllProducts().get(product.getProductID());
		ArrayList<Integer> users = this.productDAO.checkForFavProducts(p);
			for(Entry<String, User> e : this.userDAO.getAllUsers().entrySet()){
					for (Integer i : users) {	
							if(e.getValue().getID() == i){
									new MailSender(e.getValue().getEmail() ,"New SALE at eMAG!", "Product with ID: " + p.getProductID() + 
											" has been changed! Check out our Hot Offers on our website!");
							}
					}
		}
			
		m.addAttribute("product", updatedProduct);
        return "viewProduct";
    }
	
	@RequestMapping(value = "adminPage", method = RequestMethod.GET)
	public String viewAdminPage(HttpSession session, Model model) {
		if(session.getAttribute("user") == null) {
			model.addAttribute("invalidSession", "Please log in to view this page.");
			return "redirect:/login";
		}	
		try {
			HashMap<String, User> users = userDAO.getAllUsers();
			long countUsers = users.size();
			model.addAttribute("totalUsers", countUsers);
		} catch (SQLException e) {
			return "errorPage";
		}
		return "adminPage";
	}
}