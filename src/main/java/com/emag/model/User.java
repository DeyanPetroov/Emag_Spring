package com.emag.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.emag.hashing.BCrypt;
import com.emag.model.dao.UserDAO;
import com.emag.model.util.UserValidations;

public class User {
	
	private UserDAO userDAO;

	private long user_id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String profilePictureURL;
	private String address;
	private int age;
	private boolean isAdmin;
	
	private Cart cart = new Cart();
	private Order order = new Order(this);
	
	private ArrayList<Order> orderHistory = new ArrayList<>();
	private HashSet<Product> favouriteProducts = new HashSet<>();

	public User(long id, String username, String password, String first_name, String last_name, String email, String phone, int age, String profilePicture, String address) {
		this(username, password, first_name, last_name, email, age);
		this.user_id = id;
		this.profilePictureURL = profilePicture;
		this.address = address;
		this.phone = phone;
	}

	//used for registration
	public User(String username, String password, String firstName, String lastName, String email, int age) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setAge(age);
		cart.setUser(this);
	}

	// ----------GETTERS-----------

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public Order getOrder() {
		return order;
	}

	public Cart getCart() {
		return cart;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getAge() {
		return age;
	}

	public String getPhone() {
		return phone;
	}

	public long getId() {
		return user_id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	
	// -----------SETTERS-----------

	public void setFirstName(String firstName) {
		if (UserValidations.isValidName(firstName)) {
			this.firstName = firstName;
		}
		//TODO else throw exception
	}

	public void setLastName(String lastName) {
		if (UserValidations.isValidName(lastName)) {
			this.lastName = lastName;
		}
		//TODO else throw exception
	}

	public void setPhone(String phone) {
		if (UserValidations.isValidPhone(phone)) {
			this.phone = phone;
		}
		//TODO else throw exception

	}

	public void setEmail(String email) {
		if (UserValidations.isValidEmail(email)) {
			this.email = email;
		}
		//TODO else throw exception
	}

	public void setAge(int age) {
		if (age > 0) {
			this.age = age;
		}
	}

	public void setUsername(String username) {
		if (UserValidations.isValidUsername(username)) {
			this.username = username;
		}
		//TODO else throw exception
	}

	public boolean setPassword(String password) {
		if (UserValidations.isValidPassword(password)) {
			this.password = password;
			return true;
		}
		return false;
		// TODO else throw exception
	}
	
	public void setProfilePictureURL(String profilePictureURL) {
		try {
			if(UserValidations.isValidURL(profilePictureURL)) {
				this.profilePictureURL = profilePictureURL;
			}
		} catch (IOException e) {
			e.printStackTrace();
			//TODO else throw exception
		}
	}
	
	public void setAddress(String address) {
		if (UserValidations.isValidStr(address)) {
			this.address = address;
		}
	}

	// -------------METHODS-----------
	
	public void addToHistory(Order order) {
		orderHistory.add(order);
	}
	
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public void updateUser(long id, String username, String password, String first_name, String last_name, String email, String phone, int age, String profilePicture, String address) {
		this.user_id = id;
		setUsername(username);
		setPassword(password);
		setFirstName(first_name);
		setLastName(last_name);
		setEmail(email);
		setPhone(phone);
		setAge(age);
		setProfilePictureURL(profilePicture);
		setAddress(address);
		this.cart.setUser(this);
	}

	public void addOrRemoveFavourites(Product product) {
		boolean found = false;
		for(Product p : favouriteProducts) {
			if(p.getProductID() == product.getProductID()) {
				System.out.println("removed from pojo");
				found = true;
				this.favouriteProducts.remove(p);
				break;
			}
		}
		if(found == false ) {
			System.out.println("added to pojo");
			this.favouriteProducts.add(product);		
		}
	}
	
	public Set<Product> getFavouriteProducts() {
		return Collections.unmodifiableSet(this.favouriteProducts);
	}
}