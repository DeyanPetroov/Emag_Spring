package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emag.model.*;

@Component
public class ProductDAO implements IProductDAO {
	
	private static final String INSERT_PRODUCT = "INSERT INTO products(brand, price, availability, model, description, discount_percent, discount_expiration, product_picture, cateogory_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_PRODUCT_BY_ID = 
			"SELECT p.product_id, p.brand, p.price, p.model, p.availability, p.description, p.discount_percent, p.discount_expiration, p.product_picture, c.category_name FROM products AS p " + 
			"JOIN categories AS c " + 
			"ON p.product_id = ? AND p.category_id = c.category_id";
	private static final String UPDATE_PRODUCT = "UPDATE products SET brand = ?, price = ?, availability = ?, model = ?, description = ?, discount_percent = ?, discount_expiration = ?, product_picture = ?, category_id = ?";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
	private static final String GET_ALL_BY_CATEGORY = "SELECT product_id, brand, price, availability, model, description, discount_percent, discount_expiration, product_picture, category_id FROM products WHERE category_id = ?";
	
	private Connection connection;
	
	private ProductDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addProduct(Product product) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(INSERT_PRODUCT);) {
			p.setString(1, product.getBrand());
			p.setDouble(2, product.getPrice());
			p.setBoolean(3, product.getAvailability());
			p.setString(4, product.getModel());
			p.setString(5, product.getDescription());
			p.setInt(6, product.getDiscountPercent());
			p.setObject(7, product.getDiscountExpiration());
			p.setString(8, product.getProductImageURL());
			p.setInt(9, product.getCategory().getCategoryID());
		}
	}

	@Override
	public void deleteProduct(long product_id) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(DELETE_PRODUCT_BY_ID);){
			p.setLong(1, product_id);
			p.executeUpdate();
		}
	}

	@Override
	public void updateProduct(Product product) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(UPDATE_PRODUCT);){
			p.setString(1, product.getBrand());
			p.setDouble(2, product.getPrice());
			p.setBoolean(3, product.getAvailability());
			p.setString(4, product.getModel());
			p.setString(5, product.getDescription());
			p.setInt(6, product.getDiscountPercent());
			p.setObject(7, product.getDiscountExpiration());
			p.setString(8, product.getProductImageURL());
			p.setInt(9, product.getCategory().getCategoryID());
			p.executeUpdate();
		}
	}

	@Override
	public Product getProductById(long product_id) throws SQLException {
		Product product = null;
		try(PreparedStatement p = connection.prepareStatement(GET_PRODUCT_BY_ID);){
			p.setLong(1, product_id);
			System.out.println(p.toString());
			try (ResultSet resultSet = p.executeQuery()) {
				while (resultSet.next()) {
					System.out.println("heree");
					product = new Product(resultSet.getInt("product_id"), 
							resultSet.getString("brand"),
							resultSet.getString("model"),
							resultSet.getString("description"),
							resultSet.getString("product_picture"),
							resultSet.getDouble("price"),
							resultSet.getBoolean("availability"),
							resultSet.getInt("discount_percent"),
							resultSet.getDate("discount_expiration"),
							resultSet.getString("category_name"));
				}
			}
		}
		return product;
	}

	@Override
	public List<Product> getProductsByCategory(int category_id) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		ResultSet resultSet = null;
		Product product = null;
		
		try(PreparedStatement p = connection.prepareStatement(GET_ALL_BY_CATEGORY);){
			p.setInt(1, category_id);
			try {				
				resultSet = p.executeQuery();
				while (resultSet.next()) {
							product = new Product(
							resultSet.getLong("product_id"),							
							resultSet.getInt("category_id"),
							resultSet.getString("brand"),
							resultSet.getString("model"),
							resultSet.getString("description"),
							resultSet.getString("product_picture"),
							resultSet.getDouble("price"),
							resultSet.getBoolean("availability"),
							resultSet.getInt("discount_percent"),
							(java.util.Date) resultSet.getObject("discount_expiration")
							);
					sameCategoryProducts.add(product);
				}				
			}
			finally {
				resultSet.close();
			}
		}
		return sameCategoryProducts;
	}	
}