package com.emag.model;

public class Characteristic {

	private long characteristicID;
	private String name;
	private String unit;
	private int categoryID;
	private String value;
	
	//===========CONSTRUCTORS===========
	
	public Characteristic(String name, String unit, int categoryID, String value) {
		this.categoryID = categoryID;
		setName(name);
		setUnit(unit);
		this.value = value;
	}
	
	public Characteristic(long characteristicID, String name, String unit, String value) {
		setName(name);
		setUnit(unit);
		this.characteristicID = characteristicID;
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
	
	public String getUnit() {
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
	
	public void setUnit(String unit) {
		if(unit!=null&&!unit.isEmpty()) {
			this.unit = unit;
		}
	}
	
	public void setValue(String value) {
		if (value != null && !value.isEmpty()) {
			this.value = value;
		}
	}
	
	public void setCategoryID(int categoryID) {
		this.categoryID = categoryID;
	}
}
