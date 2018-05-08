package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.emag.model.Category;
import com.emag.model.Characteristic;

@Component
public class CategoryDAO implements ICategoryDAO {

	private static final String GET_ALL_CATEGORIES = "SELECT category_id, category_name FROM categories";
	private static final String GET_CATEGORIES_BY_PARENT_ID = "SELECT category_id, category_name FROM categories WHERE parent_category_id = ?";
	private static final String GET_ALL_MAIN_CATEGORIES = "SELECT category_id, category_name FROM categories WHERE parent_category_id IS NULL";
	private static final String GET_CATEGORY_BY_NAME = "SELECT category_id FROM categories WHERE category_name = ?";
	
	private static final String IS_MAIN_CATEGORY = 
			"SELECT category_id, category_name, parent_category_id FROM categories " + 
			"WHERE category_id = ? AND parent_category_id IS NULL";
	
	private Connection connection;
	
	@Autowired
	CharacteristicDAO characteristicDAO;

	private CategoryDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public HashMap<Category, ArrayList<Category>> allCategories() throws SQLException {
		HashMap<Category, ArrayList<Category>> allCategories = new HashMap<>();	
		try(PreparedStatement parentCategories = connection.prepareStatement(GET_ALL_MAIN_CATEGORIES);) {
			try(ResultSet result = parentCategories.executeQuery()){
				while (result.next()) {
					List<Characteristic> characteristics = characteristicDAO.allCategoryCharacteristics(result.getInt("category_id"));
					allCategories.put(new Category(
							result.getInt("category_id"),
							result.getString("category_name"),
							characteristics), 
							new ArrayList<Category>(getSubCategoriesByParentID(result.getInt("category_id"))));
				}
			}
		}
		return allCategories;
	}

	@Override
	public ArrayList<Category> getAllCategories() throws SQLException {
		ArrayList<Category> categories = new ArrayList<>();
		try (PreparedStatement p = connection.prepareStatement(GET_ALL_CATEGORIES);) {
			try(ResultSet resultSet = p.executeQuery()){
				while (resultSet.next()) {
					List<Characteristic> characteristics = characteristicDAO.allCategoryCharacteristics(resultSet.getInt("category_id"));
					Category c = new Category(resultSet.getInt("category_id"),resultSet.getString("category_name"),characteristics);
					categories.add(c);
				}
			}
			return categories;
		}
	}

	@Override
	public ArrayList<Category> getSubCategoriesByParentID(int parentID) throws SQLException {
		ArrayList<Category> subCategories = new ArrayList<>();
		Category category = null;
		
		try(PreparedStatement getSubCategories = connection.prepareStatement(GET_CATEGORIES_BY_PARENT_ID);){
			getSubCategories.setInt(1, parentID);
			try(ResultSet result = getSubCategories.executeQuery()){
				while (result.next()) {
					List<Characteristic> characteristics = characteristicDAO.allCategoryCharacteristics(result.getInt("category_id"));
					category = new Category(result.getInt("category_id"),result.getString("category_name"),characteristics);
					subCategories.add(category);
				}
			}
		}
		return subCategories;
	}

	@Override
	public Category getCategoryByName(String categoryName) throws SQLException {
		Category category = null;
		try(PreparedStatement getCategory = connection.prepareStatement(GET_CATEGORY_BY_NAME);){
			getCategory.setString(1, categoryName);
			try(ResultSet result = getCategory.executeQuery()){
				if(result.next()) {
					List<Characteristic> characteristics = characteristicDAO.allCategoryCharacteristics(result.getInt("category_id"));
					category = new Category(result.getInt("category_id"),categoryName, characteristics);
				}
			}
		}
		return category;
	}

	@Override
	public boolean isMainCategory(int categoryID) throws SQLException {
		try(PreparedStatement checkMainCategory = connection.prepareStatement(IS_MAIN_CATEGORY);) {
			checkMainCategory.setInt(1, categoryID);
			try(ResultSet result = checkMainCategory.executeQuery()) {
				if(result.next()) {
					return true;
				}
			}
		}
		return false;
	}
}