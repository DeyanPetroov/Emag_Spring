package com.emag.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Category {

	private int categoryID;
	private String categoryName;
	private List<Characteristic> characteristics;
	
	public Category() {
	}
		
	public Category(String categoryName, List<Characteristic> characteristics) {		
		this.categoryName = categoryName;	
		this.characteristics = characteristics;
	}	
	
	public Category(int categoryID, String categoryName, List<Characteristic> characteristics) {
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.characteristics = characteristics;
	}
	
	public List<Characteristic> getCharacteristics() {
		return Collections.unmodifiableList(this.characteristics);
	}

	public int getCategoryID() {
		return categoryID;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public void addCharacteristic(Characteristic c) {
		this.characteristics.add(c);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + categoryID;
		result = prime * result + ((categoryName == null) ? 0 : categoryName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Category other = (Category) obj;
		if (categoryID != other.categoryID)
			return false;
		if (categoryName == null) {
			if (other.categoryName != null)
				return false;
		} else if (!categoryName.equals(other.categoryName))
			return false;
		return true;
	}	
	
	
}