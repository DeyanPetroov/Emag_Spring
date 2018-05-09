package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emag.controller.MailSender;
import com.emag.model.*;
import java.sql.*;

@Component
public class ProductDAO implements IProductDAO {
	
	private static final String INSERT_PRODUCT =
			"INSERT INTO products(brand, price, availability, model, description, discount_percent, discount_expiration, " +
			"product_picture, category_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String GET_PRODUCT_BY_ID = 
			"SELECT p.product_id, p.brand, p.price, p.model, p.availability, p.description, p.discount_percent, p.discount_expiration, " +
			"p.product_picture, c.category_name FROM products AS p " + 
			"JOIN categories AS c " + 
			"ON p.product_id = ? AND p.category_id = c.category_id";
	private static final String UPDATE_PRODUCT = 
			"UPDATE products SET brand = ?, discount_percent = ?, price = ?, availability = ?, model = ?, description = ?, " +
			"discount_expiration = ?, product_picture = ? WHERE product_id = ?";
	private static final String DELETE_PRODUCT_BY_ID = "DELETE FROM products WHERE product_id = ?";
	private static final String GET_ALL_PRODUCTS = 
			"SELECT product_id, brand, price, availability, model, description, discount_percent, " +
			"discount_expiration, product_picture, category_id FROM products";
	private static final String GET_ALL_FROM_SUBCATEGORY = 
			"SELECT product_id, brand, price, availability, model, description, discount_percent, " +
			"discount_expiration, product_picture, category_id FROM products WHERE category_id = ?";
	private static final String INSERT_PRODUCT_INTO_FAVOURITES = "INSERT INTO favourite_products (user_id, product_id) VALUES (?,?)";
	private static final String GET_FAVOURITE_BY_USER_ID = "SELECT user_id, product_id FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String REMOVE_FROM_FAVOURITES = "DELETE FROM favourite_products WHERE user_id = ? AND product_id = ?";
	private static final String VIEW_FAVOURITE_PRODUCTS = "SELECT product_id FROM favourite_products WHERE user_id = ?";
	private static final String GET_PRODUCT_PICTURE = "SELECT product_picture FROM products WHERE product_id = ?";
	private static final String CHANGE_PRODUCT_PICTURE = "UPDATE products SET product_picture = ? WHERE product_id = ?";
	private static final String CHECK_IF_HAS_FAVOURITE = "SELECT user_id from favourite_products WHERE product_id = ?";
	private static final String GET_PROMO_PRODUCTS = 
			"SELECT product_id, brand, price, availability, model, description, discount_percent, discount_expiration, product_picture, category_id "
			+ "FROM products WHERE discount_percent>0";
	private static final String GET_ALL_FROM_MAIN_CATEGORY = 
			"SELECT p.product_id, p.brand, p.price, p.availability, p.model, p.description, " +
			"p.discount_percent, p.discount_expiration, p.product_picture, p.category_id " +
			"FROM products AS p " +
			"JOIN categories AS c " +
			"WHERE c.category_id = p.category_id " +
			"AND (c.category_id = ? OR c.parent_category_id = ?)";
	private static final String SORT_MAIN_CATEGORY_BY_ASCENDING_PRICE = 
			"SELECT p.product_id, p.brand, p.price, p.availability, p.model, p.description, " + 
			"p.discount_percent, p.discount_expiration, p.product_picture, p.category_id " + 
			"FROM products AS p " + 
			"JOIN categories AS c " + 
			"WHERE c.category_id = p.category_id " + 
			"AND (c.category_id = ? OR c.parent_category_id = ?) " + 
			"ORDER BY p.price";
	private static final String SORT_MAIN_CATEGORY_BY_DESCENDING_PRICE = 
			"SELECT p.product_id, p.brand, p.price, p.availability, p.model, p.description, " + 
			"p.discount_percent, p.discount_expiration, p.product_picture, p.category_id " + 
			"FROM products AS p " + 
			"JOIN categories AS c " + 
			"WHERE c.category_id = p.category_id " + 
			"AND (c.category_id = ? OR c.parent_category_id = ?) " + 
			"ORDER BY p.price DESC";
	private static final String SORT_SUBCATEGORY_BY_ASCENDING_PRICE = 
			"SELECT product_id, brand, price, availability, model, description, discount_percent, " +
			"discount_expiration, product_picture, category_id FROM products WHERE category_id = ? ORDER BY price";
	private static final String SORT_SUBCATEGORY_BY_DESCENDING_PRICE = 
			"SELECT product_id, brand, price, availability, model, description, discount_percent, " +
			"discount_expiration, product_picture, category_id FROM products WHERE category_id = ? ORDER BY price DESC";
	private static final String GET_PRODUCTS_BY_ORDER_ID = 
			"SELECT op.quantity, p.product_id, p.brand, p.price, p.availability, p.model, p.description, p.discount_percent, p.discount_expiration, " +
			"p.product_picture, p.category_id " + 
			"FROM products as p " + 
			"JOIN ordered_products AS op " + 
			"ON op.product_id = p.product_id " + 
			"AND op.order_id = ?";
	
	@Autowired
	private UserDAO userDAO;
	private Connection connection;
	private static final HashMap<Long, Product> allProducts = new HashMap<>();
	private static boolean ON_SALE = false;
	
	public boolean getSaleStatus() {
		return ON_SALE;
	}
	
	public void setSaleStatus(Boolean status) {
		ON_SALE = status;		
	}

	private ProductDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void addProduct(Product product) throws SQLException {
		try(PreparedStatement addProduct = connection.prepareStatement(INSERT_PRODUCT, Statement.RETURN_GENERATED_KEYS)) {
			addProduct.setString(1, product.getBrand());
			addProduct.setDouble(2, product.getPrice());
			addProduct.setInt(3, product.getAvailability());
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
			p.setInt(2, product.getDiscountPercent());
			p.setDouble(3, product.getPrice());
			p.setInt(4, product.getAvailability());
			p.setString(5, product.getModel());
			p.setString(6, product.getDescription());
			p.setObject(7, product.getDiscountExpiration());
			p.setString(8, product.getProductPicture());
			p.setLong(9, product.getProductID());
			p.executeUpdate();
			
			if(this.getSaleStatus()) {
				for(Entry<String, User> e : this.userDAO.getAllUsers().entrySet()){
					new MailSender(e.getValue().getEmail() ,"New products on SALE!", "There are new products on sale on our website! Check them out!");
					//TODO: send link to website in email
				}
			}
		}
	}
	
	@Override
	public ArrayList<Long> checkForFavProducts(long productID) throws SQLException {
		ArrayList<Long> users = new ArrayList<>();
		try (PreparedStatement st = connection.prepareStatement(CHECK_IF_HAS_FAVOURITE,
				Statement.RETURN_GENERATED_KEYS);) {
			st.setLong(1, productID);
			try (ResultSet result = st.executeQuery()) {
				while (result.next()) {
					long userID = result.getLong("user_id");
					users.add(userID);
				}
			}

		} catch (SQLException e) {
			System.out.println("Problem while checking for users that liked the product: " + e.getMessage());
		}
		
		return users;
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
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration")).
							withCategoryName(resultSet.getString("category_name"));
				}
			}
		}
		return product;
	}
	
	public HashMap<Long, Product> getAllProducts() throws SQLException{
		if(allProducts.isEmpty()){
			Product product = null;
			try (PreparedStatement st = DBManager.getInstance().getConnection().prepareStatement(GET_ALL_PRODUCTS); 
					ResultSet resultSet = st.executeQuery();)
			{				
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(resultSet.getInt("category_id")).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));						
					
					allProducts.put(product.getProductID(), product);
				}
			}		
		}
		return allProducts;
	}

	@Override
	public List<Product> getProductsFromSubCategory(int categoryID) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(GET_ALL_FROM_SUBCATEGORY);) {
			p.setInt(1, categoryID);
			try (ResultSet resultSet = p.executeQuery();) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					sameCategoryProducts.add(product);
				}
			}
		}
		return sameCategoryProducts;
	}
	
	public List<Product> searchProduct(String search) throws SQLException{
		ArrayList<Product> searchResults = new ArrayList<>();
		getAllProducts();
		for(Product p : allProducts.values()){
			if(p.getDescription() != null && p.getBrand() != null && p.getModel() != null){
				String description = p.getDescription().toLowerCase();
				String brand = p.getBrand().toLowerCase();
				String model = p.getModel().toLowerCase();
				if(description.contains(search.toLowerCase()) || brand.contains(search.toLowerCase()) || model.contains(search.toLowerCase())){
					searchResults.add(p);
				}
			}
			else{
				if(p.getBrand() == null){
					if(p.getDescription().contains(search) || p.getModel().contains(search)){
						searchResults.add(p);
					}
				}
			}
		}
		return Collections.unmodifiableList(searchResults);
	}

	//add or remove products from favourites in the database
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
	public List<Product> viewFavouriteProducts(User user) throws SQLException {
		List<Product> favProducts = new ArrayList<>();
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
			e.printStackTrace();
		}
		return favProducts;
	}
	
	@Override
	public List<Product> viewPromoProducts() throws SQLException {
		List<Product> promoProducts = new ArrayList<>();
		try (PreparedStatement st = connection.prepareStatement(GET_PROMO_PRODUCTS);) {
			try (ResultSet result = st.executeQuery();) {
				while (result.next()) {
					long productId = result.getLong("product_id");
					Product p = getProductById(productId);
					promoProducts.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return promoProducts;
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

	@Override
	public List<Product> getProductsFromMainCategory(int categoryID) throws SQLException {
		List<Product> mainCategoryProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(GET_ALL_FROM_MAIN_CATEGORY);) {
			p.setInt(1, categoryID);
			p.setInt(2, categoryID);		
			try (ResultSet resultSet = p.executeQuery();) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					mainCategoryProducts.add(product);
				}
			}
		}
		return mainCategoryProducts;
	}

	@Override
	public List<Product> getSortedAscendingFromMainCategory(int categoryID) throws SQLException {
		List<Product> mainCategorySortedProducts = new ArrayList<>();
		Product product = null;
		try (PreparedStatement p = connection.prepareStatement(SORT_MAIN_CATEGORY_BY_ASCENDING_PRICE);) {
			p.setInt(1, categoryID);
			p.setInt(2, categoryID);
			try (ResultSet resultSet = p.executeQuery()) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					mainCategorySortedProducts.add(product);
				}
			}
		}
		return mainCategorySortedProducts;
	}

	@Override
	public List<Product> getSortedDescendingFromMainCategory(int categoryID) throws SQLException {
		List<Product> mainCategorySortedProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(SORT_MAIN_CATEGORY_BY_DESCENDING_PRICE);) {
			p.setInt(1, categoryID);
			p.setInt(2, categoryID);		
			try (ResultSet resultSet = p.executeQuery();) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					mainCategorySortedProducts.add(product);
				}
			}
		}
		return mainCategorySortedProducts;
	}

	@Override
	public List<Product> getSortedAscendingFromSubCategory(int categoryID) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(SORT_SUBCATEGORY_BY_ASCENDING_PRICE)) {
			p.setInt(1, categoryID);
			try (ResultSet resultSet = p.executeQuery();) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					sameCategoryProducts.add(product);
				}
			}
		}
		return sameCategoryProducts;
	}

	@Override
	public List<Product> getSortedDescendingFromSubCategory(int categoryID) throws SQLException {
		List<Product> sameCategoryProducts = new ArrayList<>();
		Product product = null;

		try (PreparedStatement p = connection.prepareStatement(SORT_SUBCATEGORY_BY_DESCENDING_PRICE)) {
			p.setInt(1, categoryID);
			try (ResultSet resultSet = p.executeQuery();) {
				while (resultSet.next()) {
					product = new Product().
							withProductID(resultSet.getLong("product_id")).
							withCategoryID(categoryID).
							withBrand(resultSet.getString("brand")).
							withModel(resultSet.getString("model")).
							withDescription(resultSet.getString("description")).
							withProductPicture(resultSet.getString("product_picture")).
							withPrice(resultSet.getDouble("price")).
							withAvailability(resultSet.getInt("availability")).
							withDiscountPercent(resultSet.getInt("discount_percent")).
							withDiscountExpiration(resultSet.getDate("discount_expiration"));		
					sameCategoryProducts.add(product);
				}
			}
		}
		return sameCategoryProducts;
	}

	@Override
	public Map<Product, Integer> orderProducts(long orderID) throws SQLException {
		Map<Product, Integer> products = new HashMap<>();
		Product product = null;
		
		try(PreparedStatement getProductsForOrder = connection.prepareStatement(GET_PRODUCTS_BY_ORDER_ID);) {
			getProductsForOrder.setLong(1, orderID);
			try(ResultSet result = getProductsForOrder.executeQuery()){
				while(result.next()) {
					product = new Product().
							withProductID(result.getLong("product_id")).
							withCategoryID(result.getInt("category_id")).
							withBrand(result.getString("brand")).
							withModel(result.getString("model")).
							withDescription(result.getString("description")).
							withProductPicture(result.getString("product_picture")).
							withPrice(result.getDouble("price")).
							withAvailability(result.getInt("availability")).
							withDiscountPercent(result.getInt("discount_percent")).
							withDiscountExpiration(result.getDate("discount_expiration"));	
					products.put(product, result.getInt("quantity"));
				}
			}
		}
		return products;
	}
}