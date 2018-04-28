package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.Category;
import com.emag.model.dao.CategoryDAO;

@Controller
public class CategoryController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String getAllCategories(Model model, HttpSession session) {
		HashMap<String, ArrayList<Category>> categories = new HashMap<>();
		
		try {
			categories = categoryDAO.allCategories();
			model.addAttribute("categories", categories);
		}
		catch (SQLException e) {
			return "errorPage";
		}
		return "index";
	}	
}
