package com.emag.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.emag.hashing.BCrypt;
import com.emag.model.User;
import com.emag.model.dao.UserDAO;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/login", method = RequestMethod.GET )
	public String login(HttpServletRequest request) {
		return "login";
	}
	
	@RequestMapping(value = "/errorPage", method = RequestMethod.GET)
	public String error(HttpServletRequest request) {
		return "errorPage";
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
			String existingEmail = userDAO.emailExists(email);
			String existingUsername = userDAO.usernameExists(username);
			if (existingEmail != null || existingUsername != null) {
				model.addAttribute("error", "Username and/or email might be taken, try again!");
				return "register";
			} 
			else {
				User u = new User(username, password, first_name, last_name, email, age);
				try {
					this.userDAO.saveUser(u);
					u.setPassword(u.hashPassword(u.getPassword()));
				} catch (SQLException e) {
					e.getMessage();
					return "errorPage";
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
			e.getMessage();
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
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.GET)
	public String editProfile(HttpSession session) {
		return "editProfile";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.GET)
	public String changePassword(HttpSession session) {
		return "changePassword";
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public String changePassword(Model model, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmedNewPassword = request.getParameter("confirmedNewPassword");
		
		if(!BCrypt.checkpw(oldPassword, user.getPassword())) {
			model.addAttribute("wrongOldPassword", "Wrong old password");
			model.addAttribute("oldPass", oldPassword);
			return "changePassword";
		}
		else {
			if (!newPassword.equals(confirmedNewPassword)) {
				model.addAttribute("passwordsMissmatch", "The new password doesn't match the confirmed one");
				return "changePassword";
			}
			else {
				try {
					userDAO.changePassword(user, newPassword);
					user.setPassword(user.hashPassword(newPassword));
				} catch (SQLException e) {
					return "errorPage";
				}
			}
		}
		return "profile";
	}
	
	@RequestMapping(value = "/editProfile", method = RequestMethod.POST)
	public String editProfile(Model model, HttpServletRequest request, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String oldEmail = request.getParameter("oldEmail");
		
		String firstName = request.getParameter("firstname").trim();
		String lastName = request.getParameter("lastname").trim();
		String address = request.getParameter("address").trim();
		String email = request.getParameter("email").trim();
		String phone = request.getParameter("phone").trim();
		String imageURL = request.getParameter("image").trim();
		
		try {
			if (!oldEmail.equals(email) && userDAO.emailExists(email) != null) {
				model.addAttribute("error", "Email is taken, try again!");
				return "editProfile";
			} 
			else {
				User userNewData = new User(user.getId(), user.getUsername(), user.getPassword(), firstName, lastName, email, phone, user.getAge(), imageURL, address);
				userDAO.updateUser(userNewData);
				user.updateUser(user.getId(), user.getUsername(), user.getPassword(), firstName, lastName, email, phone, user.getAge(), imageURL, address);
			}
		}
		//TODO make an abstract exception handler		
		catch (SQLException e) {
			e.getMessage();
			return "errorPage";
		}
		return "profile";
	}

	
	//get all
	//get existing user	
	//profile pic pri editing profile
	//add to favourites	
}

