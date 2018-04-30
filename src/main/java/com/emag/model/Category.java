package com.emag.model;

public class Category {

	private int categoryID;
	private String categoryName;
	
	public Category() {
		
	}
		
	public Category(int categoryID, String categoryName) {		
		this.categoryID = categoryID;
		this.categoryName = categoryName;		
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