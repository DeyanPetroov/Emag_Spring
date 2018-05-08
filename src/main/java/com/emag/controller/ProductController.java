package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
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
	
	//sort
	@RequestMapping(value = "/category/{categoryID}/sort/{order}", method = RequestMethod.GET)
	public String sortProducts(@PathVariable("categoryID") Integer categoryID, @PathVariable("order") String order, Model model) {
		List<Product> products = new ArrayList<>();
		try {
			boolean isMain = categoryDAO.isMainCategory(categoryID);
			if(isMain) {
				if(order.equals("asc")) {
					products = productDAO.getSortedAscendingFromMainCategory(categoryID);
				}
				else if(order.equals("desc")) {
					products = productDAO.getSortedDescendingFromMainCategory(categoryID);
				}
			}
			else {
				if(order.equals("asc")) {
					products = productDAO.getSortedAscendingFromSubCategory(categoryID);
				}
				else {
					products = productDAO.getSortedDescendingFromSubCategory(categoryID);
				}
			}
		}
		catch(SQLException e) {
			return "errorPage";
		}
		model.addAttribute("products", products);
		return "products";
	}
	
	//get products by category
	@RequestMapping(value = "/category/{categoryID}", method = RequestMethod.GET)
	public String getProductsByCategory(Model model, @PathVariable("categoryID") Integer categoryID){
		List<Product> products = new ArrayList<>();
		try {
			boolean isMain = categoryDAO.isMainCategory(categoryID);
			if(isMain) {
				products = productDAO.getProductsFromMainCategory(categoryID);
			}
			else {
				products = productDAO.getProductsFromSubCategory(categoryID);
			}
			model.addAttribute("products", products);
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
	
	@RequestMapping(value = "/promo", method = {RequestMethod.GET, RequestMethod.POST})
	public String viewPromotions(HttpServletRequest request, Model model) throws SQLException{
		
		String promo = null;
		if(request.getParameter("promo") != null){
			promo = request.getParameter("promo");
		}
		else{
			promo = (String) request.getAttribute("promo");
		}
		
		Collection<Product> promoProducts = this.productDAO.viewPromoProducts();
		
		if(!promoProducts.isEmpty()){
			for (Product product : promoProducts) {
				System.out.println(product);
			}
			
			model.addAttribute("products", promoProducts);
		}
		else{
			model.addAttribute("message", "No products found.");
		}
		return "products";
		
	}
	
	//TODO
	//-->mandatory
	
	//send email when a product is on sale
	//every product should have a picture(take from DB)
	//fix menu with characteristics to contain only the ones for the specific category
	//format order date
	//can add more characteristics for a product
	//better error page + 404 + exceptions
	//change favourite button when already in favourites
	
	//-->bonus
	
	//slider for prices
	//send email for forgotten password
	//ajax for favourites, sorting, characteristics menu
	//make reviews with raitings, show average raiting
	//deal with JSON
}
