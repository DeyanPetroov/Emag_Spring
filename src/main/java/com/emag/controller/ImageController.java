package com.emag.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;

import javax.servlet.annotation.MultipartConfig;
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

import com.emag.model.User;
import com.emag.model.dao.UserDAO;

@Controller
@MultipartConfig
public class ImageController {
	
	private static final String FILE_PATH = "\\Users\\Nadya\\Desktop";

	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.GET)
	public String viewUploadForm() {	
		return "editProfile";
	}
	
	@RequestMapping(value = "/uploadProfilePicture", method = RequestMethod.POST)
	public String finishUploading(@RequestParam("image") MultipartFile uploadedFile, Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		String profilePicture = uploadedFile.getOriginalFilename();
		File serverFile = new File(FILE_PATH + profilePicture);
		try {
			Files.copy(uploadedFile.getInputStream(), serverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			return "errorPage";
		}
		
		try {
			userDAO.changeProfilePicture(profilePicture, user.getId());
			user.setProfilePictureURL(profilePicture);
		} catch (SQLException e) {
			return "errorPage";
		}
		model.addAttribute("filename", profilePicture);
		return "profile";
	}
	

	@RequestMapping(value="/download/{profilePicture:.+}", method=RequestMethod.GET)
	public void downloadFile(HttpServletResponse resp, @PathVariable("profilePicture") String profilePicture) throws IOException {
		System.out.println(profilePicture);
		File serverFile = new File(FILE_PATH + profilePicture);
		Files.copy(serverFile.toPath(), resp.getOutputStream());
	}
}
