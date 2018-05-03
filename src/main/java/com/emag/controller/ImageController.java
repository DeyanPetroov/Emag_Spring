package com.emag.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

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
import org.springframework.web.multipart.MultipartFile;

import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.ProductDAO;
import com.emag.model.dao.UserDAO;

@Controller
public class ImageController {
	
	private static final String USER_IMAGE_FILE_PATH = "C:\\Users\\Nadya\\Desktop\\images\\";
	private static final String PRODUCT_IMAGE_FILE_PATH = "C:\\Users\\Nadya\\Desktop\\images\\products";

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.GET)
	public String viewUploadForm(Model model, HttpServletRequest request, HttpSession session) {
		return "editProfile";
	}
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
	public String finishUploading(@RequestParam("image") MultipartFile uploadedFile, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String profilePicture = uploadedFile.getOriginalFilename();
		File serverFile = new File(USER_IMAGE_FILE_PATH + profilePicture); //should have better name for the current user
		try {
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			userDAO.changeProfilePicture(profilePicture, user.getID());
			user.setProfilePictureURL(profilePicture);
		} catch (IOException | SQLException e) {
			return "errorPage";
		}
		
		model.addAttribute("profilePicture", profilePicture);
		return "profile";
	}
	
	@RequestMapping(value = "/uploadProductPicture", method = RequestMethod.POST)
	public String uploadProductPicture(@RequestParam("image") MultipartFile uploadedFile, Model model, HttpServletRequest request) {
		long productID = Integer.valueOf(request.getParameter("productID"));
		String productPicture = uploadedFile.getOriginalFilename();
		File serverFile = new File(PRODUCT_IMAGE_FILE_PATH + productPicture);
		try {
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			Product product = productDAO.getProductById(productID);
			productDAO.changeProductPicture(productPicture, productID);
			product.setProductPicture(productPicture);
		} 
		catch (SQLException | IOException e) {
			return "errorPage";
		}
		model.addAttribute("productPicture", productPicture);
		return "viewProduct";
	}
		
	@RequestMapping(value="/download/{profilePicture:.+}", method=RequestMethod.GET)
	public void downloadPicture(Model model, HttpServletResponse resp, @PathVariable("profilePicture") String picture) throws IOException {
		File serverFile = new File(USER_IMAGE_FILE_PATH + picture);
		try {
			Files.copy(serverFile.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
