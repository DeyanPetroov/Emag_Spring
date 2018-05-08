package com.emag.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.emag.model.Characteristic;
import com.mysql.jdbc.Statement;

@Component
public class CharacteristicDAO implements ICharacteristicDAO {
	
	private static final String INSERT_CHARACTERISTIC = "INSERT INTO characteristics (name, unit, value) VALUES(?,?, ?)";
	private static final String REMOVE_CHARACTERISTIC = "DELETE FROM characteristics WHERE characteristic_id = ?";
	private static final String EDIT_CHARACTERISTIC = "UPDATE characteristics SET name = ?, unit = ?, value = ? WHERE characteristic_id = ?";
	
	private static final String INSERT_CATEGORY_CHARACTERISTICS = "INSERT INTO category_characteristics (category_id, characteristic_id) VALUES(?,?)";
	private static final String REMOVE_CATEGORY_CHARACTERISTIC = "DELETE FROM category_characteristics WHERE category_id = ? AND characteristic_id = ?";
	private static final String GET_ALL_CATEGORY_CHARACTERISTICS = 
			"SELECT cat.characteristic_id, c.name, c.unit, c.value FROM characteristics AS c " +
			"JOIN category_characteristics AS cat " +
			"WHERE cat.category_id = ? AND c.characteristic_id = cat.characteristic_id ";
	private static final String GET_ALL_CHARACTERISTICS = "SELECT characteristic_id, name, unit, value FROM characteristics";

	private Connection connection;
	
	private CharacteristicDAO() {
		connection = DBManager.getInstance().getConnection();
	}
	
	@Override
	public void insertCharacteristic(Characteristic characteristic) throws SQLException {
		try(PreparedStatement addCharacteristic = connection.prepareStatement(INSERT_CHARACTERISTIC, Statement.RETURN_GENERATED_KEYS)){
			addCharacteristic.setString(1, characteristic.getName());
			addCharacteristic.setString(2, characteristic.getUnit());
			addCharacteristic.setString(3, characteristic.getValue());
			addCharacteristic.executeUpdate();
			try(ResultSet result = addCharacteristic.getGeneratedKeys()){
				if(result.next()) {
					characteristic.setCharacteristicID(result.getLong(1));
				}
			}
		}
	}
	
	@Override
	public void insertCategoryCharacteristics(List<Characteristic> characteristics, int categoryID) throws SQLException {
		for(Characteristic c : characteristics) {
			try (PreparedStatement add = connection.prepareStatement(INSERT_CATEGORY_CHARACTERISTICS);) {
				add.setInt(1, categoryID);
				add.setLong(2, c.getCharacteristicID());
				add.executeUpdate();
			}
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
	public void editCharacteristic(Characteristic characteristic) throws SQLException {
		try(PreparedStatement edit = connection.prepareStatement(EDIT_CHARACTERISTIC);){
			edit.setString(1, characteristic.getName());
			edit.setString(2, characteristic.getUnit());
			edit.setString(3, characteristic.getValue());
			edit.setLong(4, characteristic.getCharacteristicID());
			edit.executeUpdate();
		}
	}

	@Override
	public List<Characteristic> allCategoryCharacteristics(int categoryID) throws SQLException {
		List<Characteristic> categoryCharacteristics = new ArrayList<>();
		try(PreparedStatement getAllChars = connection.prepareStatement(GET_ALL_CATEGORY_CHARACTERISTICS);) {
			getAllChars.setLong(1, categoryID);
			try(ResultSet result = getAllChars.executeQuery()) {
				while(result.next()) {
					Characteristic characteristic = new Characteristic(
							result.getLong("characteristic_id"),
							result.getString("name"),
							result.getString("unit"),
							result.getString("value"));
					categoryCharacteristics.add(characteristic);
				}
			}
		}
		return categoryCharacteristics;
	}

	@Override
	public void removeCategoryCharacteristic(int categoryID, long characteristicID) throws SQLException {
		try(PreparedStatement remove = connection.prepareStatement(REMOVE_CATEGORY_CHARACTERISTIC);){
			remove.setLong(1, categoryID);
			remove.setLong(2, characteristicID);
			remove.executeUpdate();
		}	
	}

	@Override
	public List<Characteristic> allCharacteristics() throws Exception {
		List<Characteristic> characteristics = new ArrayList<>();
		try(PreparedStatement getAll = connection.prepareStatement(GET_ALL_CHARACTERISTICS);){
			try(ResultSet result = getAll.executeQuery()){
				while(result.next()) {
					Characteristic characteristic = new Characteristic(
							result.getLong("characteristic_id"),
							result.getString("name"), 
							result.getString("unit"), 
							result.getString("value"));
					characteristics.add(characteristic);
				}
			}
		}
		return characteristics;
	}
}