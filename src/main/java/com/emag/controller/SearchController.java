package com.emag.controller;

import java.sql.SQLException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Product;
import com.emag.model.dao.ProductDAO;



@Controller
public class SearchController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST})
	public String search(HttpServletRequest request, Model model) throws SQLException{
		
		String search = null;
		if(request.getParameter("search") != null){
			search = request.getParameter("search");
		}
		else{
			search = (String) request.getAttribute("search");
		}
		
		Collection<Product> searchProd = this.productDAO.searchProduct(search);
		
		if(!searchProd.isEmpty()){
			for (Product product : searchProd) {
				System.out.println(product);
			}
			
			model.addAttribute("products", searchProd);
		}
		else{
			model.addAttribute("message", "Няма намерени продукти.");
		}
		return "products";
		
	}
	
}
