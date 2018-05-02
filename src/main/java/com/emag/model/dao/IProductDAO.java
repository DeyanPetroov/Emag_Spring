package com.emag.model.dao;

import java.util.List;
import java.util.Set;

import com.emag.model.Product;
import com.emag.model.User;

public interface IProductDAO {
	
	void addProduct(Product product) throws Exception;
	void deleteProduct(long product_id) throws Exception;
	void updateProduct(Product product) throws Exception;
	Product getProductById(long product_id) throws Exception;
	List<Product> getProductsByCategory(int category_id) throws Exception;
	void addOrRemoveFavouriteProduct(User user, Product product) throws Exception;
	Set<Product> viewFavouriteProducts(User user) throws Exception;
}
