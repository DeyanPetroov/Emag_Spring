package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.emag.model.Characteristic;
import com.emag.model.Product;
import com.mysql.jdbc.Statement;


public class CharacteristicDAO implements ICharacteristicDAO {
	
	private static final String INSERT_CHARACTERISTIC = "INSERT INTO characteristics (name, unit, category_id) VALUES(?,?,?)";
	private static final String REMOVE_CHARACTERISTIC = "DELETE FROM characteristics WHERE characteristic_id = ?";
	private static final String EDIT_CHARACTERISTIC = "UPDATE characteristics SET name = ?, unit = ?, category_id = ? WHERE characteristic_id = ?";
	
	private static final String INSERT_PRODUCT_CHARACTERISTICS = "INSERT INTO products_characteristics (product_id, characteristic_id, value) VALUES(?,?,?)";
	private static final String REMOVE_PRODUCT_CHARACTERISTICS = "DELETE FROM products_characteristics WHERE product_id = ? AND characteristic_id = ?";
	private static final String GET_ALL_PRODUCT_CHARACTERISTICS = 
			"SELECT c.name, c.unit, c.category_id, pc.value FROM products_characteristics AS pc " +
			"JOIN characteristics AS c " +
			"ON c.characteristic_id = pc.characteristic_id " + 
			"JOIN products AS p " + 
			"ON p.product_id = pc.product_id "+
			"WHERE p.product_id = ? ";

	private Connection connection;
	
	private CharacteristicDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void insertCharacteristic(Characteristic characteristic) throws Exception {
		try(PreparedStatement addCharacteristic = connection.prepareStatement(INSERT_CHARACTERISTIC, Statement.RETURN_GENERATED_KEYS)){
			addCharacteristic.setString(1, characteristic.getName());
			addCharacteristic.setInt(2, characteristic.getUnit());
			addCharacteristic.setInt(3, characteristic.getCategoryID());
			addCharacteristic.executeUpdate();
			try(ResultSet result = addCharacteristic.getGeneratedKeys()){
				if(result.next()) {
					characteristic.setCharacteristicID(result.getLong(1));
				}
			}
		}
	}
	
	@Override
	public void insertProductCharacteristics(Characteristic characteristic, Product product) throws Exception {
		try(PreparedStatement add = connection.prepareStatement(INSERT_PRODUCT_CHARACTERISTICS);) {
			add.setLong(1, product.getProductID());
			add.setLong(2, characteristic.getCharacteristicID());
			add.setString(3, characteristic.getValue());
			add.executeUpdate();
		}
	}

	@Override
	public void removeCharacteristic(long characteristicID) throws Exception {
		try(PreparedStatement remove = connection.prepareStatement(REMOVE_CHARACTERISTIC);){
			remove.setLong(1, characteristicID);
			remove.executeUpdate();
		}
	}

	@Override
	public void editCharacteristic(Characteristic characteristic) throws Exception {
		try(PreparedStatement edit = connection.prepareStatement(EDIT_CHARACTERISTIC);){
			edit.setString(1, characteristic.getName());
			edit.setInt(2, characteristic.getUnit());
			edit.setInt(3, characteristic.getCategoryID());
			edit.setLong(4, characteristic.getCharacteristicID());
			edit.executeUpdate();
		}
	}

	@Override
	public List<Characteristic> allProductCharacteristics(long productID) throws Exception {
		List<Characteristic> productCharacteristics = new ArrayList<>();
		try(PreparedStatement getAllChars = connection.prepareStatement(GET_ALL_PRODUCT_CHARACTERISTICS);) {
			getAllChars.setLong(1, productID);
			try(ResultSet result = getAllChars.executeQuery()) {
				while(result.next()) {
					Characteristic characteristic = new Characteristic(
							result.getString("name"),
							result.getInt("unit"),
							result.getInt("category_id"),
							result.getString("value"));
					productCharacteristics.add(characteristic);
				}
			}
		}
		return productCharacteristics;
	}

	@Override
	public void removeProductCharacteristic(long productID, long characteristicID) throws Exception {
		try(PreparedStatement remove = connection.prepareStatement(REMOVE_PRODUCT_CHARACTERISTICS);){
			remove.setLong(1, productID);
			remove.setLong(2, characteristicID);
			remove.executeUpdate();
		}
		
	}
}
