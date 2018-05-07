package com.emag.model.dao;

import java.util.ArrayList;
import java.util.HashMap;

import com.emag.model.Category;

public interface ICategoryDAO {
	
	ArrayList<Category> getAllCategories() throws Exception;
	ArrayList<Category> getSubCategoriesByParentID(int parentID) throws Exception;
	HashMap<Category, ArrayList<Category>> allCategories() throws Exception;
	Category getCategoryByName(String categoryName) throws Exception;
	boolean isMainCategory(int categoryID) throws Exception;
}
