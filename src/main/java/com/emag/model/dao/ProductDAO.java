package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emag.model.*;
import com.mysql.jdbc.Statement;

@Component
public class ProductDAO implements IProductDAO {
	
	private static final String INSERT_PRODUCT = "INSERT INTO products(brand, price, availability, model, description, discount_percent, discount_expiration, product_picture, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_PRODUCT_BY_ID = 
			"SELECT p.product_id, p.brand, p.price, p.model, p.availability, p.description, p.discount_percent, p.discount_expiration, p.product_picture, c.category_name FROM products AS p " + 
			"JOIN categories AS c " + 
			"ON p.product_id = ? AND p.category_id = c.category_id";
	private static final String UPDATE_PRODUCT = "UPDATE products SET brand = ?, price = ?, availability = ?, model = ?, description = ?, discount_percent = ?, discount_expiration = ?, product_picture = ?, category_id = ? WHERE product_id = ?";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
	private static final String GET_ALL_BY_CATEGORY = "SELECT product_id, brand, price, availability, model, description, discount_percent, discount_expiration, product_picture, category_id FROM products WHERE category_id = ?";
	private static final String GET_ID_OF_PRODUCT = "SELECT product_id FROM products WHERE brand = ? AND model = ?";
	private static final String INSERT_PRODUCT_INTO_FAVOURITES = "INSERT INTO favourite_products (user_id, product_id) VALUES (?,?)";
	private static final String GET_FAVOURITE_BY_USER_ID = "SELECT user_id, product_id FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String REMOVE_FROM_FAVOURITES = "DELETE FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String VIEW_FAVOURITE_PRODUCTS = "SELECT product_id FROM favourite_products WHERE user_id = ?";
	
	private Connection connection;

	private ProductDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addProduct(Product product) throws SQLException {
		try(PreparedStatement addProduct = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
			addProduct.setString(1, product.getBrand());
			addProduct.setDouble(2, product.getPrice());
			addProduct.setBoolean(3, product.getAvailability());
			addProduct.setString(4, product.getModel());
			addProduct.setString(5, product.getDescription());
			addProduct.setInt(6, product.getDiscountPercent());
			addProduct.setObject(7, product.getDiscountExpiration());
			addProduct.setString(8, product.getProductImageURL());
			addProduct.setInt(9, product.getCategory().getCategoryID());
			addProduct.executeUpdate();
			
			try(ResultSet result = addProduct.getGeneratedKeys()){
				if(result.next()) {
					product.setProductID(result.getLong(1));
				}
			}
		}
	}

	@Override
	public void deleteProduct(long productID) throws SQLException {
		try(PreparedStatement p = connection.prepareStatement(DELETE_PRODUCT_BY_ID);){
			p.setLong(1, productID);
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
			p.setLong(10, product.getProductID());
			p.executeUpdate();
		}
	}

	@Override
	public Product getProductById(long productID) throws SQLException {
		Product product = null;
		try(PreparedStatement p = connection.prepareStatement(GET_PRODUCT_BY_ID);){
			p.setLong(1, productID);
			try (ResultSet resultSet = p.executeQuery()) {
				while (resultSet.next()) {
					product = new Product(
							resultSet.getInt("product_id"), 
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
	public List<Product> getProductsByCategory(int categoryID) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(GET_ALL_BY_CATEGORY);) {
			p.setInt(1, categoryID);
			try (ResultSet resultSet = p.executeQuery();) {
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
							resultSet.getDate("discount_expiration"));
					sameCategoryProducts.add(product);
				}
			}
		}
		return sameCategoryProducts;
	}

	// add product to favourites in the database
	@Override
	public void addOrRemoveFavouriteProduct(User user, Product product) throws SQLException {
		if (favouriteExists(user, product)) {
			try (PreparedStatement removeFromFav = connection.prepareStatement(REMOVE_FROM_FAVOURITES);) {
				removeFromFav.setLong(1, user.getId());
				removeFromFav.setLong(2, product.getProductID());
				removeFromFav.executeUpdate();
			}
		} 
		else {
			try (PreparedStatement addToFav = connection.prepareStatement(INSERT_PRODUCT_INTO_FAVOURITES);) {
				addToFav.setLong(1, user.getId());
				addToFav.setLong(2, product.getProductID());
				addToFav.executeUpdate();
			}
		}
	}

	private boolean favouriteExists(User user, Product product) throws SQLException {
		try (PreparedStatement getFav = connection.prepareStatement(GET_FAVOURITE_BY_USER_ID);) {
			getFav.setLong(1, user.getId());
			getFav.setLong(2, product.getProductID());
			try (ResultSet result = getFav.executeQuery()) {
				if (result.next()) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Set<Product> viewFavouriteProducts(User user) throws Exception {
		Set<Product> favProducts = new HashSet<>();
		try (PreparedStatement st = connection.prepareStatement(VIEW_FAVOURITE_PRODUCTS);) {
			st.setLong(1, user.getId());
			try (ResultSet result = st.executeQuery();) {
				while (result.next()) {
					long productId = result.getLong("product_id");
					Product p = getProductById(productId);
					favProducts.add(p);
				}
			}
		} catch (SQLException e) {
			// TODO
		}
		return favProducts;
	}
}