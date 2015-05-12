package model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
	
	private List<ShoppingCartItem> items;
	private int numberOfItems;
	private double total;
	
	public ShoppingCart() {
		items = new ArrayList<ShoppingCartItem>();
		numberOfItems = 0;
		total = 0;
	}
	
	public synchronized void addItem(Recipe recipe) {
		boolean newItem = true;

		for (ShoppingCartItem scItem : items) {

			if (scItem.getProduct().getId() == recipe.getId()) {

				newItem = false;
				scItem.incrementQuantity();
			}
		}

		if (newItem) {
			ShoppingCartItem scItem = new ShoppingCartItem(recipe);
			items.add(scItem);
		}
	}
	
	public synchronized void update(Recipe recipe, String quantity) {

		short qty = -1;


		qty = Short.parseShort(quantity);

		if (qty >= 0) {

			ShoppingCartItem item = null;

			for (ShoppingCartItem scItem : items) {

				if (scItem.getProduct().getId() == recipe.getId()) {

					if (qty != 0) {
                   // set item quantity to new value
						scItem.setQuantity(qty);
					} else {

						item = scItem;
						break;
					}
				}
			}	

			if (item != null) {

				items.remove(item);
			}
		}
	}
	
	public synchronized List<ShoppingCartItem> getItems() {

		return items;
	}

	public synchronized double getSubtotal() {

		double amount = 0;

		for (ShoppingCartItem scItem : items) {

			Recipe recipe = (Recipe) scItem.getProduct();
			amount += (scItem.getQuantity() * recipe.getPrice());
		}

		return amount;
	}
	
	public synchronized void calculateTotal(String surcharge) {

		double amount = 0;

		double s = Double.parseDouble(surcharge);

		amount = this.getSubtotal();
		amount += s;

		total = amount;
	}
	
	public synchronized double getTotal() {

		return total;
	}
	
	public synchronized void clear() {
		items.clear();
		numberOfItems = 0;
		total = 0;
	}
	
}
