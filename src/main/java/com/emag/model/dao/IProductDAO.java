package com.emag.model.dao;

import java.util.List;
import java.util.Set;

import com.emag.model.Product;
import com.emag.model.User;

public interface IProductDAO {
	
	void addProduct(Product product) throws Exception;
	void deleteProduct(long productID) throws Exception;
	void updateProduct(Product product) throws Exception;
	Product getProductById(long productID) throws Exception;
	List<Product> getProductsFromMainCategory(int categoryID) throws Exception;
	List<Product> getProductsFromSubCategory(int categoryID) throws Exception;
	void addOrRemoveFavouriteProduct(User user, Product product) throws Exception;
	Set<Product> viewFavouriteProducts(User user) throws Exception;
	void changeProductPicture(String productPicture, long productID) throws Exception;
	public String getProfilePicture(long productID) throws Exception;
	Set<Product> viewPromoProducts() throws Exception;
	List<Product> getSortedAscendingFromMainCategory(int categoryID) throws Exception; 
	List<Product> getSortedDescendingFromMainCategory(int categoryID) throws Exception; 
	List<Product> getSortedAscendingFromSubCategory(int categoryID) throws Exception; 
	List<Product> getSortedDescendingFromSubCategory(int categoryID) throws Exception; 
}
