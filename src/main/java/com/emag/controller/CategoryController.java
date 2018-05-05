package com.emag.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.emag.model.Category;
import com.emag.model.dao.CategoryDAO;
import com.sun.glass.ui.Application;

@Controller
public class CategoryController {

	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ServletContext context;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public String getAllCategories(Model model, HttpSession session) {
		HashMap<Category, ArrayList<Category>> categories = new HashMap<>();
		try {
			categories = categoryDAO.allCategories();
			context.setAttribute("categories", categories);
		} catch (SQLException e) {
			return "errorPage";
		}
		return "index";
	}
}
