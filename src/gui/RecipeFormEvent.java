package gui;

import java.util.EventObject;

public class RecipeFormEvent extends EventObject{
	
	private String name;
	private String price;

	public RecipeFormEvent(Object source) {
		super(source);
	}

	public RecipeFormEvent(Object source, String name, String price) {
		super(source);
		this.name = name;
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "RecipeFormEvent [name=" + name + ", price=" + price + "]";
	}

}
