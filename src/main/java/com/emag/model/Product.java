package com.emag.model;

import java.util.Date;

public class Product {

	private long productID;
	private String brand;
	private String model;
	private String description;
	private String productImageURL;
	private double price;
	private boolean availability;
	private int discountPercent;
	private Date discountExpiration;
	private Category category = new Category();

	
	// ==============GETTERS=====================
	
	
	public Product(String brand, String model, String description, String productImageURL, double price,
			boolean availability, int discountPercent, Date discountExpiration) {
		this.brand = brand;
		this.model = model;
		this.description = description;
		this.productImageURL = productImageURL;
		this.price = price;
		this.availability = availability;
		this.discountPercent = discountPercent;
		this.discountExpiration = discountExpiration;
	}

	public Product(long productID, int categoryID, String brand, String model, String description,
			String productImageURL, double price, boolean availability, int discountPercent, Date discountExpiration) {
		this(brand, model, description, productImageURL, price, availability, discountPercent, discountExpiration);
		this.productID = productID;
		this.category.setCategoryID(categoryID);
	}

	public Product(long productID, String brand, String model, String description, String productImageURL, double price,
			boolean availability, int discountPercent, Date discountExpiration, String categoryName) {
		this(brand, model, description, productImageURL, price, availability, discountPercent, discountExpiration);
		this.productID = productID;
		this.category.setCategoryName(categoryName);
	}
	
	public Product(int categoryID, String brand, String model, String description, String productImageURL, double price, boolean availability, int discountPercent, Date discountExpiration) {
		this(brand, model, description, productImageURL, price, availability, discountPercent, discountExpiration);
		this.category.setCategoryID(categoryID);
	}

	public String getModel() {
		return model;
	}

	public String getBrand() {
		return brand;
	}

	public double getPrice() {
		return price;
	}

	public long getProductID() {
		return productID;
	}

	public String getDescription() {
		return description;
	}

	public String getProductImageURL() {
		return productImageURL;
	}

	public boolean getAvailability() {
		return availability;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public Date getDiscountExpiration() {
		return discountExpiration;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setProductID(long productID) {
		this.productID = productID;
	}
}