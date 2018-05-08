package com.emag.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

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

import com.emag.model.Category;
import com.emag.model.Characteristic;
import com.emag.model.Product;
import com.emag.model.User;
import com.emag.model.dao.CategoryDAO;
import com.emag.model.dao.CharacteristicDAO;
import com.emag.model.dao.ProductDAO;
import com.emag.model.dao.UserDAO;

@Controller
public class ImageController {
	
	private static final String USER_IMAGE_FILE_PATH = "C:\\Users\\Nadya\\Desktop\\images\\";
	private static final String PRODUCT_IMAGE_FILE_PATH = "C:\\Users\\Nadya\\Desktop\\images\\products\\";

	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private CharacteristicDAO characteristicDAO;
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.GET)
	public String viewUploadForm(Model model, HttpServletRequest request, HttpSession session) {
		return "editProfile";
	}
	
	@RequestMapping(value = "/uploadProductPicture", method = RequestMethod.GET)
	public String viewUpload(Model model, HttpServletRequest request, HttpSession session) {
		return "editProduct";
	}
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
	public String finishUploading(@RequestParam("image") MultipartFile uploadedFile, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String profilePicture = user.getEmail() + uploadedFile.getOriginalFilename();
		File serverFile = new File(USER_IMAGE_FILE_PATH + profilePicture);
		try {
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			userDAO.changeProfilePicture(profilePicture, user.getID());
			user.setProfilePictureURL(profilePicture);
		} catch (IOException | SQLException e) {
			return "errorPage";
		}
		
		model.addAttribute("profilePicture", profilePicture);
		return "editProfile";
	}
	
	@RequestMapping(value = "/uploadProductPicture", method = RequestMethod.POST)
	public String uploadProductPicture(@RequestParam("image") MultipartFile uploadedFile, Model model, HttpServletRequest request) {
		long productID = Integer.valueOf(request.getParameter("productID"));
		String productPicture = productID + uploadedFile.getOriginalFilename();
		File serverFile = new File(PRODUCT_IMAGE_FILE_PATH + productPicture);
		Product product = null;
		try {
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			product = productDAO.getProductById(productID);
			productDAO.changeProductPicture(productPicture, productID);
			product.setProductPicture(productPicture);
		} 
		catch (SQLException | IOException e) {
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
		
		model.addAttribute("categories", categories);
		model.addAttribute("characteristics", characteristics);
		model.addAttribute("productPicture", productPicture);
		model.addAttribute("product", product);
		return "editProduct";
	}
		
	@RequestMapping(value="/download/user-profile/{profilePicture:.+}", method=RequestMethod.GET)
	public void downloadUserPicture(Model model, HttpServletRequest request, HttpServletResponse resp, @PathVariable("profilePicture") String profilePicture) throws IOException {
		downloadPictures(USER_IMAGE_FILE_PATH, profilePicture, resp);
	}
	
	@RequestMapping(value="/download/product_picture/{productPicture:.+}", method=RequestMethod.GET)
	public void downloadProductPicture(Model model, HttpServletRequest request, HttpServletResponse resp, @PathVariable("productPicture") String productPicture) throws IOException {
		downloadPictures(PRODUCT_IMAGE_FILE_PATH, productPicture, resp);
	}

	private void downloadPictures(String path, String picture, HttpServletResponse resp) {
		File serverFile = new File(path + picture);
		try {
			Files.copy(serverFile.toPath(), resp.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
