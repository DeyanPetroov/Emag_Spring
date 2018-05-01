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
		return user.getOrder().getTotalCost();
	}

	public boolean addOrRemoveCartProduct(Product p, int quantity) {
		if(p.getAvailability() == false) {
			return false;
		}
		
		boolean inCart = false;
		for(Entry<Product, Integer> entry : this.products.entrySet()) {
			if(entry.getKey().getProductID() == p.getProductID()) {
				if(quantity >= entry.getValue()) {
					this.products.remove(entry.getKey(), entry.getValue());				
				}
				else {
					this.products.put(entry.getKey(), entry.getValue() - quantity);
				}
				System.out.println("removed from cart");
				inCart = true;
			}
		}
		
		if(inCart == false ) {
			System.out.println("added to cart");
			this.products.put(p, quantity);
		}		
		user.getOrder().setTotalCost(user.getOrder().getTotalCost() + p.getPrice() * quantity);
		return true;
	}

	public void removeFromCart(Product p, int quantity) {
		if (this.products.containsKey(p)) {
			if (products.get(p) > quantity) {
				this.products.put(p, products.get(p) - quantity);
			} else {
				this.products.remove(p);
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