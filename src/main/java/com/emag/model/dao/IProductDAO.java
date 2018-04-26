package com.emag.model.dao;

import java.util.List;

import com.emag.model.Product;

public interface IProductDAO {
	
	void addProduct(Product product) throws Exception;
	void deleteProduct(long product_id) throws Exception;
	void updateProduct(Product product) throws Exception;
	Product getProductById(long product_id) throws Exception;
	List<Product> getProductsByCategory(int category_id) throws Exception;
}
