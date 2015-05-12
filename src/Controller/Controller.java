package Controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import model.Database;
import model.Order;
import model.Pay;
import model.Recipe;
import model.State;
import model.Table;
import model.Type;
import model.User;
import gui.FormEvent;
import gui.RecipeFormEvent;

public class Controller {
	
	Database db = new Database();
	
	//// get tables
	
	public List<Order> getOrders(){
		return db.getOrders();
	}

	public List<Recipe> getRecipes(){
		return db.getRecipes();
	}

	public List<Table> getTables(){
		return db.getTables();
	}

	public List<User> getUsers(){
		return db.getUsers();
	}
	
	//// remove rows
	
	public void removeOrder(int index){
		db.removeOrder(index);
	}
	
	public void removeRecipes(int index){
		db.removeRecipe(index);
	}
	
	public void removeTable(int index){
		db.removeTable(index);;
	}
	
	public void removeUser(int index){
		db.removeUser(index);;
	}
	
	//// add items to the database
	
	public void addOrder(FormEvent ev){
		String buyer = ev.getBuyer();
		String count = ev.getCount();
		int stateCat = ev.getStateCategory();
		String typeCat = ev.getType();
		String coupon = ev.getCoupon();
		boolean couponCheck = ev.isCouponCheck();
		String payCommand = ev.getPayCommand();
		
		State state = null;
		switch(stateCat){
		case 0:
			state = State.newOrder;
			break;
		case 1:
			state = State.activeOrder;
			break;
		case 2:
			state = State.finishedOrder;
			break;
		}
		
		Type type=null;
		if(typeCat.equals("TakeAway")){
			type = Type.TakeAway;
		} else if(typeCat.equals("Other")){
			type = Type.Other;
		}
		
		Pay pay=null;
		if(payCommand.equals("Paid")){
			pay = Pay.paid;
		} else if(payCommand.equals("Not Paid")){
			pay = Pay.not_paid;
		}
		
		Order order = new Order(buyer, count, state, type, coupon, couponCheck, pay);
		db.addOrder(order);
	}
	
	public void addRecipeOrder(RecipeFormEvent ev){
		String name = ev.getName();
		int price = Integer.parseInt(ev.getPrice());
		
		Recipe recipe = new Recipe(name,price);
		db.addRecipe(recipe);
		System.out.println("con "+recipe);
	}
	
	//// load from database
	
	public void orderLoad() throws SQLException{
		db.orderLoad();
	}
	
	public void recipeLoad() throws SQLException{
		db.recipeLoad();
	}
	
	public void tableLoad() throws SQLException{
		db.tableLoad();
	}
	
	public void userLoad() throws SQLException{
		db.userLoad();
	}
	
	//// save on database
	
	public void orderSave() throws SQLException{
		db.orderSave();
	}
	
	public void recipeSave() throws SQLException{
		db.recipeSave();
	}
	
	public void tableSave() throws SQLException{
		db.tableSave();
	}
	
	public void userSave() throws SQLException{
		db.userSave();
	}
	
	//// db connection

	public void connect() throws Exception{
		db.connect();
	}
	
	public void close(){
		db.disconnect();
	}
	
	public void saveToFile(File file) throws IOException{
		db.saveToFile(file);
	}
	
	public void loadFromFile(File file) throws IOException{
		db.loadFromFile(file);
	}
	
}


