package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.emag.model.*;
import java.sql.*;

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
	private static final String INSERT_PRODUCT_INTO_FAVOURITES = "INSERT INTO favourite_products (user_id, product_id) VALUES (?,?)";
	private static final String GET_FAVOURITE_BY_USER_ID = "SELECT user_id, product_id FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String REMOVE_FROM_FAVOURITES = "DELETE FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String VIEW_FAVOURITE_PRODUCTS = "SELECT product_id FROM favourite_products WHERE user_id = ?";
	private static final String GET_PRODUCT_PICTURE = "SELECT product_picture FROM products WHERE product_id = ?";
	private static final String CHANGE_PRODUCT_PICTURE = "UPDATE products SET product_picture = ? WHERE product_id = ?";
	
	
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
			addProduct.setString(8, product.getProductPicture());
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
			p.setString(8, product.getProductPicture());
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
				if (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getBoolean("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration")).
							withCategoryName(resultSet.getString("category_name"));
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
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(resultSet.getInt("category_id")).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getBoolean("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
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
				removeFromFav.setLong(1, user.getID());
				removeFromFav.setLong(2, product.getProductID());
				removeFromFav.executeUpdate();
			}
		} 
		else {
			try (PreparedStatement addToFav = connection.prepareStatement(INSERT_PRODUCT_INTO_FAVOURITES);) {
				addToFav.setLong(1, user.getID());
				addToFav.setLong(2, product.getProductID());
				addToFav.executeUpdate();
			}
		}
	}

	private boolean favouriteExists(User user, Product product) throws SQLException {
		try (PreparedStatement getFav = connection.prepareStatement(GET_FAVOURITE_BY_USER_ID);) {
			getFav.setLong(1, user.getID());
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
	public Set<Product> viewFavouriteProducts(User user) throws SQLException {
		Set<Product> favProducts = new HashSet<>();
		try (PreparedStatement st = connection.prepareStatement(VIEW_FAVOURITE_PRODUCTS);) {
			st.setLong(1, user.getID());
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

	@Override
	public void changeProductPicture(String productPicture, long productID) throws SQLException {
		try(PreparedStatement changePicture = connection.prepareStatement(CHANGE_PRODUCT_PICTURE);){
			changePicture.setString(1, productPicture);
			changePicture.setLong(2, productID);
			changePicture.executeUpdate();
		}
	}
	
	@Override
	public String getProfilePicture(long productID) throws SQLException {
		String picture = null;
		try(PreparedStatement getPicture = connection.prepareStatement(GET_PRODUCT_PICTURE);) {
			getPicture.setLong(1, productID);
			try(ResultSet result = getPicture.executeQuery()){
				if(result.next()) {
					picture = result.getString("product_picture");
				}
			}
		}
		return picture;
	}
}