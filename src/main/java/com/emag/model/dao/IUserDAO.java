package com.emag.model.dao;
import java.util.HashMap;
import java.util.Set;

import com.emag.model.*;

public interface IUserDAO {

	User getUserByID(long userID) throws Exception;
	String usernameExists(String username) throws Exception;
	String emailExists(String email) throws Exception;
	void saveUser(User u) throws Exception;
	void updateUser(User u) throws Exception;
	void changePassword(User u, String password) throws Exception;
	void deleteUserByID(User user) throws Exception;
	void changeProfilePicture(String profilePicture, long userID) throws Exception;
	HashMap<String, User> getAllUsers() throws Exception;
	User getExistingUser(String username, String password) throws Exception;
	boolean isAdmin(User user) throws Exception;
	String getProfilePicture(long userID) throws Exception;
}
