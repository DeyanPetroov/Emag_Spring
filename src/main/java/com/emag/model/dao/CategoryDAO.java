package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.emag.model.Category;

@Component
public class CategoryDAO implements ICategoryDAO{

	
	private static final String GET_ALL_CATEGORIES = "SELECT category_id, category_name FROM categories";
		
		private Connection connection;
		
		private CategoryDAO() {
			connection = DBManager.getInstance().getConnection();
		}
	
	@Override
	public ArrayList<Category> getAllCategories() throws Exception {
		ArrayList<Category> categories = new ArrayList<>();
		ResultSet resultSet = null;
		
		try(PreparedStatement p = connection.prepareStatement(GET_ALL_CATEGORIES);){
			try{
				resultSet = p.executeQuery();
				while (resultSet.next()) {				
					Category c = new Category(resultSet.getInt("category_id"), resultSet.getString("category_name"));
					categories.add(c);
				}				
			}
			finally {
				resultSet.close();
				System.gc();
			}
		
		return categories;
	}
}
}
