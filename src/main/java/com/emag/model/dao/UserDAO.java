package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emag.hashing.BCrypt;
import com.emag.model.*;

@Component
public class UserDAO implements IUserDAO {

	private static final String GET_EXISTING_USER_BY_EMAIL = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE email = ?";
	private static final String GET_EXISTING_USER_BY_USERNAME = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE username = ?";
	private static final String GET_USER_BY_USERNAME = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE username = ?";
	private static final String INSERT_USER = "INSERT INTO users (username, password, first_name, last_name, email, age) VALUES (?,?,?,?,?,?)";
	private static final String GET_USER_BY_ID = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users WHERE user_id = ?";
	private static final String GET_ALL_USERS = "SELECT user_id, username, password, first_name, last_name, email, phone, age, profile_picture, address FROM users";
	private static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, email = ?, phone = ?, age = ?, profile_picture = ?, address = ? WHERE user_id = ?";
	private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE user_id = ?";
	private static final String CHANGE_PASSWORD = "UPDATE users SET password = ? WHERE user_id = ?";
	private static final String CHANGE_PROFILE_PICTURE = "UPDATE users SET profile_picture = ? WHERE user_id = ?";
	private static final String CHECK_IF_IS_ADMIN = "SELECT is_admin FROM users WHERE username = ?";
	private static final String GET_PROFILE_PICTURE = "SELECT profile_picture FROM users WHERE user_id = ?";	
	
	private Connection connection;
	private static final HashMap<String, User> allUsers = new HashMap<>();

	private UserDAO() {
		connection = DBManager.getInstance().getConnection();
	}

	@Override
	public User getUserByID(long userID) throws SQLException {
		User user = null;
		try (PreparedStatement selectUserById = connection.prepareStatement(GET_USER_BY_ID);) {
			selectUserById.setLong(1, userID);
			try (ResultSet resultSet = selectUserById.executeQuery();) {
				while (resultSet.next()) {
					user = new User(
						resultSet.getLong("user_id"),
						resultSet.getString("username"), 
						resultSet.getString("password"),
						resultSet.getString("first_name"),
						resultSet.getString("last_name"),
						resultSet.getString("email"),
						resultSet.getString("phone"), 
						resultSet.getInt("age"),
						resultSet.getString("profile_picture"),
						resultSet.getString("address"));
				}
			}
		}
		return user;
	}

	@Override
	public boolean isAdmin(User user) throws SQLException {
		try (PreparedStatement checkAdmin = connection.prepareStatement(CHECK_IF_IS_ADMIN);) {
			String username = this.usernameExists(user.getUsername());
			checkAdmin.setString(1, username);
			try (ResultSet result = checkAdmin.executeQuery()) {
				if (result.next()) {
					if (result.getInt("is_admin") == 1) {
						return true;
					}
				}
			}
			return false;
		}
	}

	// insert user in the database -------> maybe synchronized ?
	@Override
	public void saveUser(User u) throws SQLException {
		try (PreparedStatement saveUser = connection.prepareStatement(INSERT_USER);) {
			String hashedPassword = u.hashPassword(u.getPassword());
			saveUser.setString(1, u.getUsername());
			saveUser.setString(2, hashedPassword);
			saveUser.setString(3, u.getFirstName());
			saveUser.setString(4, u.getLastName());
			saveUser.setString(5, u.getEmail());
			saveUser.setInt(6, u.getAge());
			saveUser.executeUpdate();
		}
	}

	// update user in the database
	@Override
	public void updateUser(User u) throws SQLException {
		try (PreparedStatement updateUser = connection.prepareStatement(UPDATE_USER);) {
			updateUser.setString(1, u.getFirstName());
			updateUser.setString(2, u.getLastName());
			updateUser.setString(3, u.getEmail());
			updateUser.setString(4, u.getPhone());
			updateUser.setInt(5, u.getAge());
			updateUser.setString(6, u.getProfilePictureURL());
			updateUser.setString(7, u.getAddress());
			updateUser.setLong(8, u.getID());
			updateUser.executeUpdate();
		}
	}

	// get hashmap of all users
	@Override
	public HashMap<String, User> getAllUsers() throws SQLException {
		if (allUsers.isEmpty()) {
			try (PreparedStatement selectAllUsers = connection.prepareStatement(GET_ALL_USERS);) {
				try (ResultSet resultSet = selectAllUsers.executeQuery();) {
					while (resultSet.next()) {
						User user = new User(resultSet.getLong("user_id"),
								resultSet.getString("username"),
								resultSet.getString("password"), 
								resultSet.getString("first_name"),
								resultSet.getString("last_name"), 
								resultSet.getString("email"),
								resultSet.getString("phone"), 
								resultSet.getInt("age"),
								resultSet.getString("profile_picture"), 
								resultSet.getString("address"));
						allUsers.put(user.getUsername(), user);
					}
				}
			}
		}
		return allUsers;
	}

	// change password of user
	@Override
	public void changePassword(User u, String password) throws SQLException {
		try (PreparedStatement changePass = connection.prepareStatement(CHANGE_PASSWORD);) {
			String hashedPass = u.hashPassword(password);
			changePass.setString(1, hashedPass);
			changePass.setLong(2, u.getID());
			changePass.executeUpdate();
		}
	}

	//delete user from the database
	@Override
	public void deleteUserByID(User user) throws SQLException {
		try (PreparedStatement deleteUserByID = connection.prepareStatement(DELETE_USER_BY_ID);) {
			deleteUserByID.setLong(1, user.getID());
			deleteUserByID.executeUpdate();
		}
	}

	//get User after validating password and username
	@Override
	public User getExistingUser(String username, String password) throws SQLException {
		User user = null;
		try (PreparedStatement getExistingUser = connection.prepareStatement(GET_USER_BY_USERNAME);) {
			getExistingUser.setString(1, username);
			try (ResultSet result = getExistingUser.executeQuery()) {
				if (result.next()) {
					String hashedPassword = result.getString("password");
					if(BCrypt.checkpw(password, hashedPassword)) {
						user = getUserByID(result.getInt("user_id"));
					}
				}
			}
		}
		return user;
	}

	//search for an existing user in the database
	@Override
	public String usernameExists(String username) throws SQLException {
		try(PreparedStatement usernameExists = connection.prepareStatement(GET_EXISTING_USER_BY_USERNAME);){
			usernameExists.setString(1, username);
			try(ResultSet result = usernameExists.executeQuery()){
				if(result.next()) {
					return username;
				}
			}
		}
		return null;
	}
	
	@Override
	public String emailExists(String email) throws SQLException {
		try (PreparedStatement emailExists = connection.prepareStatement(GET_EXISTING_USER_BY_EMAIL);) {
			emailExists.setString(1, email);
			try (ResultSet result = emailExists.executeQuery()) {
				if (result.next()) {
					return email;
				}
			}
		}
		return null;
	}

	public void changeProfilePicture(String profilePicture, long id) throws SQLException {
		try(PreparedStatement changePicture = connection.prepareStatement(CHANGE_PROFILE_PICTURE);) {
			changePicture.setString(1, profilePicture);
			changePicture.setLong(2, id);
			changePicture.executeUpdate();
		}	
	}

	@Override
	public String getProfilePicture(long userID) throws SQLException {
		String picture = null;
		try(PreparedStatement getPicture = connection.prepareStatement(GET_PROFILE_PICTURE);) {
			getPicture.setLong(1, userID);
			try(ResultSet result = getPicture.executeQuery()){
				if(result.next()) {
					picture = result.getString("profile_picture");
				}
			}
		}
		return picture;
	}
}