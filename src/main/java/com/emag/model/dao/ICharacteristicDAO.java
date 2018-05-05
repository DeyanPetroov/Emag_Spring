package com.emag.model.dao;

import java.util.List;

import com.emag.model.Characteristic;
import com.emag.model.Product;

public interface ICharacteristicDAO {
	
	void insertCharacteristic(Characteristic characteristic) throws Exception;
	void removeCharacteristic(long characteristicID) throws Exception;
	void editCharacteristic(Characteristic characteristic) throws Exception;
	void insertCategoryCharacteristics(List<Characteristic> characteristics, int categoryID) throws Exception;
	List<Characteristic> allCategoryCharacteristics(int categoryID) throws Exception;
	List<Characteristic> allCharacteristics() throws Exception;
	void removeCategoryCharacteristic(int categoryID, long characteristicID) throws Exception; 
}
