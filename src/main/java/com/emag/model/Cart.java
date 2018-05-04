package com.emag.model;

import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Cart {

	private User user;
	private Map<Product, Integer> products = new TreeMap<Product, Integer>((Product p1, Product p2) -> {
		return p1.getBrand().compareTo(p2.getBrand());
	});

	public Map<Product, Integer> getProducts() {
		return Collections.unmodifiableMap(products);
	}

	public double getTotalCost() {
		double totalCost = 0;
		for(Entry<Product, Integer> prod : this.products.entrySet()) {
			totalCost += prod.getKey().getPrice() * prod.getValue();
		}
		return totalCost;
	}

	public boolean addToCart(Product p, int quantity) {
		if (p.getAvailability() == false) {
			return false;
		}

		boolean inCart = false;;
		for (Entry<Product, Integer> entry : this.products.entrySet()) {
			if (entry.getKey().getProductID() == p.getProductID()) {
				entry.setValue(entry.getValue() + quantity);
				user.getOrder().setTotalCost(user.getOrder().getTotalCost() + p.getPrice() * quantity);
				inCart = true;
				break;
			}
		}
		
		if(!inCart) {
			this.products.put(p, quantity);
		}
		
		return true;
	}

	public void removeFromCart(Product p, int quantity) {
		for (Entry<Product, Integer> entry : this.products.entrySet()) {
			if (entry.getKey().getProductID() == p.getProductID()) {
				if (entry.getValue() > quantity) {
					entry.setValue(entry.getValue() - quantity);
				}
				else {
					this.products.remove(entry.getKey());
				}
				break;
			}
		}
	}
	
	public void emptyCart() {
		this.products.clear();
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getUser() {
		return user;
	}
}