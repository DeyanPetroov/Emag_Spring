package com.emag.model.dao;

import java.util.List;

import com.emag.model.Characteristic;
import com.emag.model.Product;

public interface ICharacteristicDAO {
	
	void insertCharacteristic(Characteristic characteristic) throws Exception;
	void removeCharacteristic(long characteristicID) throws Exception;
	void editCharacteristic(Characteristic characteristic) throws Exception;
	void insertProductCharacteristics(Characteristic characteristic, Product product) throws Exception;
	List<Characteristic> allProductCharacteristics(long productID) throws Exception;
	void removeProductCharacteristic(long productID, long characteristicID) throws Exception; 
}
