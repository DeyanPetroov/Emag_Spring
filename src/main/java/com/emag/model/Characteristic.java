package com.emag.model;

public class Characteristic {

	private long characteristicID;
	private String name;
	private int unit;
	private int categoryID;
	private String value;
	
	//===========CONSTRUCTORS===========
	
	public Characteristic(String name, int unit, int categoryID, String value) {
		setName(name);
		setUnit(unit);
		this.categoryID = categoryID;
	}
	
	//===========GETTERS===========
	
	public long getCharacteristicID() {
		return characteristicID;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCategoryID() {
		return categoryID;
	}
	
	public int getUnit() {
		return unit;
	}
	
	public String getValue() {
		return value;
	}
	
	//==========SETTERS=============
	
	public void setCharacteristicID(long characteristicID) {
		this.characteristicID = characteristicID;
	}
	
	public void setName(String name) {
		if (name != null && !name.isEmpty()) {
			this.name = name;
		}
	}
	
	public void setUnit(int unit) {
		if (unit >= 0) {
			this.unit = unit;
		}
	}
	
	public void setValue(String value) {
		if (value != null && !value.isEmpty()) {
			this.value = value;
		}
	}
}
