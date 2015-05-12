package model;

import java.io.Serializable;

public class Recipe implements Serializable{
	
	private static final long serialVersionUID = 331512540236867947L;
	private static int recipeCount = 1;
	private int id;
	private String name;
	private int price;

	public Recipe(String name,int price) {
		this.name = name;
		this.price = price;
		
		this.id = recipeCount;
		recipeCount++;
	}
	
	public Recipe(int id,String name,int price){
		this(name,price);
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", price=" + price + "]";
	}
	
	
}
