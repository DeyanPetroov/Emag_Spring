package com.emag.model.dao;

import java.util.ArrayList;

import com.emag.model.Category;

public interface ICategoryDAO {
	ArrayList<Category> getAllCategories() throws Exception;
}
