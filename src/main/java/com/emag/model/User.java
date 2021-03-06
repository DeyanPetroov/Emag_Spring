package com.emag.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.emag.hashing.BCrypt;
import com.emag.model.util.UserValidations;

public class User {
	
	private long userID;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String profilePictureURL;
	private String address;
	private int age;
	private boolean admin;
	
	private Cart cart = new Cart();
	private Order order = new Order(this, address, this.getCart().getTotalCost(), this.getCart().getProducts());
	
	private HashSet<Product> favouriteProducts = new HashSet<>();
	private List<Order> orderHistory = new ArrayList<>();
	
	public User(long userID, String username, String firstName, String lastName, String email) {
		this.userID = userID;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(long id, String username, String password, String firstName, String lastName, String email, String phone, int age, String profilePicture, String address) {
		this(username, password, firstName, lastName, email);
		this.userID = id;
		this.profilePictureURL = profilePicture;
		this.address = address;
		this.phone = phone;
		this.age = age;
	}

	//used for registration
	public User(String username, String password, String firstName, String lastName, String email) {
		setFirstName(firstName);
		setLastName(lastName);
		setUsername(username);
		setPassword(password);
		setEmail(email);
		cart.setUser(this);
		this.admin = false;
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

	public long getID() {
		return userID;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	
	public boolean isAdmin() {
		return admin;
	}
	
	public Set<Product> getFavouriteProducts() {
		return Collections.unmodifiableSet(this.favouriteProducts);
	}
	
	public List<Order> getOrderHistory() {
		return Collections.unmodifiableList(this.orderHistory);
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
	
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	// -------------METHODS-----------
	
	public void addToHistory(Order order) {
		orderHistory.add(order);
	}
	
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}
	
	public void updateUser(long id, String username, String password, String first_name, String last_name, String email, String phone, int age, String profilePicture, String address) {
		this.userID = id;
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
				found = true;
				this.favouriteProducts.remove(p);
				break;
			}
		}		
		if(found == false ) {
			this.favouriteProducts.add(product);		
		}
	}
}