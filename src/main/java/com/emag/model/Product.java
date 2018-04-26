package com.emag.model;

import java.util.Date;

public class Product {

	public enum Category {
		PHONES(1), COMPUTERS(2), TV(3), PHOTO(4);

		private int category_id;

		private Category(int id) {
			this.category_id = id;
		}

		public int getCategory_id() {
			return category_id;
		}
	}

	private long product_id;
	private int category_id;
	private String name;
	private String brand;
	private String model;
	private String description;
	private String productImageURL;
	private double price;
	private int availability;
	private int discountPercent;
	private Date discountExpiration;

	
	// ==============GETTERS=====================

	public Product(int category_id, String brand, String model, String description,
			String productImageURL, double price, int availability, int discountPercent, Date discountExpiration) {
		this.category_id = category_id;
		this.brand = brand;
		this.model = model;
		this.description = description;
		this.productImageURL = productImageURL;
		this.price = price;
		this.availability = availability;
		this.discountPercent = discountPercent;
		this.discountExpiration = discountExpiration;
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

	public long getProduct_id() {
		return product_id;
	}

	public String getDescription() {
		return description;
	}

	public int getCategory_id() {
		return category_id;
	}
	
	public String getProductImageURL() {
		return productImageURL;
	}

	public int getAvailability() {
		return availability;
	}

	public int getDiscountPercent() {
		return discountPercent;
	}

	public Date getDiscountExpiration() {
		return discountExpiration;
	}
	
}