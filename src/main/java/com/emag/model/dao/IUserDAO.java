package com.emag.model.dao;
import java.util.HashMap;
import java.util.List;

import com.emag.model.*;

public interface IUserDAO {

	User getByID(long id) throws Exception;
	String usernameExists(String username) throws Exception;
	String emailExists(String email) throws Exception;
	void saveUser(User u) throws Exception;
	void updateUser(User u) throws Exception;
	void changePassword(User u, String password) throws Exception;
	void deleteUserById(User user) throws Exception;
	void addProductToFavourites(User user, Product product) throws Exception;
	void removeProductFromFavourites(User user, Product product) throws Exception;
	void changeProfilePicture(String profilePicture, long id) throws Exception;
	List<Order> getAllUserOrders(int user_id) throws Exception;	
	HashMap<String, User> getAllUsers() throws Exception;
	User getExistingUser(String username, String password) throws Exception;
}
