package com.emag.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.model.User;
import com.emag.model.dao.UserDAO;

@Controller
public class UserController {

	@Autowired
	UserDAO userDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET )
	public String login(HttpServletRequest request) {
		return "login";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET )
	public String register(HttpServletRequest request) {
		return "register";	
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, HttpSession session) {
		return "index";	
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST )
	public String login(Model model, HttpSession session, HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = null;
		try {
			user = this.userDAO.getExistingUser(username, password);
			
			if(user!=null) {
				session.setAttribute("user", user);
				session.setAttribute("logged", true);
				session.setMaxInactiveInterval(60*60);
				return "index";
			}	
			else {
				model.addAttribute("error", "Invalid username or password");
				//throw new WrongUserDataException();
				return "login";
			}
		}
		catch (SQLException e) {
			e.getMessage();
			return "login";			
		}
		
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST )
	public String register(Model model, HttpSession session, HttpServletRequest request) {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String first_name = request.getParameter("first_name");
		String last_name = request.getParameter("last_name");
		String email = request.getParameter("email");

		Integer age = Integer.valueOf(request.getParameter("age"));

		try {
			String existingUserParameter = userDAO.userExists(username, email);
			if (existingUserParameter != null) {
				return "register";
			} 
			else {
				User u = new User(username, password, first_name, last_name, email, age);
				try {
					this.userDAO.saveUser(u);
					u.setPassword(u.hashPassword());
				} catch (SQLException e) {
					e.getMessage();
					return "register";
				}

				u = this.userDAO.getExistingUser(username, password);
				if (u != null) {					
					session.setAttribute("user", u);
					session.setMaxInactiveInterval(3000);
					session.setAttribute("logged", true);
				} 
				else {
					return "register";
				}
			}
		}
		//TODO make an abstract exception handler		
		catch (SQLException e) {
			e.printStackTrace();
			return "register";
		}
		
		return "index";		
	}
	
	@RequestMapping(value = "/user/account", method = RequestMethod.GET)
	public String getProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (user != null) {
			model.addAttribute(user.getFirstName());
			model.addAttribute(user.getLastName());
			model.addAttribute(user.getEmail());
			model.addAttribute(user.getAddress());
			model.addAttribute(user.getPhone());
			model.addAttribute(user.getProfilePictureURL());
			return "profile";
		}
		return "index";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profile(HttpSession session) {
		return "profile";
	}
	
	//get all
	//get existing user	
	//profile pic pri editing profile
	//add to favourites	
}

