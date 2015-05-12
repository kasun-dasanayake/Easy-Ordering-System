package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Database {

	private LinkedList<Order> orders;
	private LinkedList<Recipe> recipes;
	private LinkedList<Table> tables;
	private LinkedList<User> users;
	
	private Connection con;
	
	public Database(){
		orders = new LinkedList<Order>();
		recipes = new LinkedList<Recipe>();
		tables= new LinkedList<Table>();
		users = new LinkedList<User>();
	}
	
	public void connect() throws Exception{
		
		if(con != null) return;
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			throw new Exception("Driver not found");
		}
		
		String url = "jdbc:mysql://localhost:3306/restaurant";
		con = DriverManager.getConnection(url, "root", "");

	}

	public void disconnect(){
		if(con != null){
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void orderSave() throws SQLException{
		
		String checkSql = "Select count(*) as count from orders where id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		
		String insertSql = "INSERT INTO orders (id, buyer, count, stateCategory, type, coupon, couponCheck,pay) VALUES (?,?,?,?,?,?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
		
		String updateSql = "Update orders set buyer=?, count=?, stateCategory=?, type=?, coupon=?, couponCheck=?,pay=? WHERE id=?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		for(Order order: orders){
			int id = order.getId();
			String buyer = order.getBuyer();
			String count = order.getCount();
			State state = order.getStateCategory();
			Type type = order.getType();
			String coupon = order.getCoupon();
			boolean couponCheck = order.isCouponCheck();
			Pay pay = order.getPayCommand();
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			int counter = checkResult.getInt(1);
			if(counter == 0){
				System.out.println(id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, buyer);
				insertStatement.setString(col++, count);
				insertStatement.setString(col++, state.name());
				insertStatement.setString(col++, type.name());
				insertStatement.setString(col++, coupon);
				insertStatement.setBoolean(col++, couponCheck);
				insertStatement.setString(col++, pay.name());
				
				insertStatement.executeUpdate();
			} else {
				System.out.println("upt "+id);
				int col = 1;
				updateStatement.setString(col++, buyer);
				updateStatement.setString(col++, count);
				updateStatement.setString(col++, state.name());
				updateStatement.setString(col++, type.name());
				updateStatement.setString(col++, coupon);
				updateStatement.setBoolean(col++, couponCheck);
				updateStatement.setString(col++, pay.name());
				updateStatement.setInt(col++, id);
				
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void orderLoad() throws SQLException{
		orders.clear();
		
		String sql = "SELECT id, buyer, count, stateCategory, type, coupon, couponCheck, pay FROM orders ORDER BY id";
		Statement selectStatement = con.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()){
			int id = results.getInt("id");
			String buyer = results.getString("buyer");
			String count = results.getString("count");
			String state = results.getString("stateCategory");
			String type = results.getString("type");
			String coupon = results.getString("coupon");
			boolean couponCheck = results.getBoolean("couponCheck");
			String pay = results.getString("pay");
			Order order = new Order(id,buyer, count, State.valueOf(state), Type.valueOf(type), coupon, couponCheck, Pay.valueOf(pay));
			orders.add(order);
		}
		
		results.close();
		selectStatement.close();
	}
	
	public void addOrder(Order order){
		orders.add(order);
	}
	
	public void removeOrder(int index){
		orders.remove(index);
	}
	
	public List<Order> getOrders(){
		return Collections.unmodifiableList(orders);
	}
	
	public void saveToFile(File file) throws IOException{
		FileOutputStream fos = new FileOutputStream(file);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		
		Order[] ordersCollection = orders.toArray(new Order[orders.size()]);
		
		oos.writeObject(ordersCollection);
		oos.close();
	}
	
	public void loadFromFile(File file) throws IOException{
		FileInputStream fis = new FileInputStream(file);
		ObjectInputStream ois = new ObjectInputStream(fis);
		
		try {
			Order[] ordersCollection = (Order[]) ois.readObject();
			orders.clear();
			orders.addAll(Arrays.asList(ordersCollection));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		ois.close();
	}
	
	public void recipeSave() throws SQLException{
		
		String checkSql = "SELECT Count(*) AS count FROM recipes WHERE id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		
		String insertSql = "INSERT INTO recipes (id, name, price) VALUES (?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
		
		String updateSql = "Update recipes set name=?, price=? WHERE id=?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		for(Recipe recipe: recipes){
			int id = recipe.getId();
			String name = recipe.getName();
			int price = recipe.getPrice();
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			int counter = checkResult.getInt(1);
			if(counter == 0){
				System.out.println("recipe "+id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setInt(col++, price);
				
				insertStatement.executeUpdate();
			} else {
				System.out.println("Recipe upt "+id);
				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setInt(col++, price);
				updateStatement.setInt(col++, id);
				
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void recipeLoad() throws SQLException{
		recipes.clear();
		
		String sql = "SELECT id, name, price FROM recipes ORDER BY id";
		Statement selectStatement = con.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()){
			int id = results.getInt("id");
			String name = results.getString("name");
			int price = results.getInt("price");
			Recipe recipe = new Recipe(id,name,price);
			recipes.add(recipe);
		}
		
		results.close();
		selectStatement.close();
	}
	
	public void addRecipe(Recipe recipe){
		System.out.println(recipe);
		recipes.add(recipe);
	}
	
	public void removeRecipe(int index){
		recipes.remove(index);
	}
	
	public List<Recipe> getRecipes(){
		return Collections.unmodifiableList(recipes);
	}
	
	public void tableSave() throws SQLException{
		
		String checkSql = "SELECT Count(*) AS count FROM tables WHERE id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		
		String insertSql = "INSERT INTO tables (id, name, available) VALUES (?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
		
		String updateSql = "Update tables set name=?, available=? WHERE id=?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		for(Table table: tables){
			int id = table.getId();
			String name = table.getName();
			String available = table.getAvailable();
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			int counter = checkResult.getInt(1);
			if(counter == 0){
				System.out.println("table "+id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setString(col++, available);
				
				insertStatement.executeUpdate();
			} else {
				System.out.println("table upt "+id);
				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setString(col++, available);
				updateStatement.setInt(col++, id);
				
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void tableLoad() throws SQLException{
		tables.clear();
		
		String sql = "SELECT id, name, available FROM tables ORDER BY id";
		Statement selectStatement = con.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()){
			int id = results.getInt("id");
			String name = results.getString("name");
			String available = results.getString("available");
			Table table = new Table(id,name,available);
			tables.add(table);
		}
		
		results.close();
		selectStatement.close();
	}
	
	public void addTable(Table table){
		tables.add(table);
	}
	
	public void removeTable(int index){
		tables.remove(index);
	}
	
	public List<Table> getTables(){
		return Collections.unmodifiableList(tables);
	}
	
public void userSave() throws SQLException{
		
		String checkSql = "SELECT Count(*) AS count FROM users WHERE id=?";
		PreparedStatement checkStmt = con.prepareStatement(checkSql);
		
		String insertSql = "INSERT INTO users (id, name, acesslevel) VALUES (?,?,?)";
		PreparedStatement insertStatement = con.prepareStatement(insertSql);
		
		String updateSql = "Update users set name=?, accesslevel=? WHERE id=?";
		PreparedStatement updateStatement = con.prepareStatement(updateSql);
		
		for(User user: users){
			int id = user.getId();
			String name = user.getName();
			int accesslevel = user.getAccessLevel();
			checkStmt.setInt(1, id);
			
			ResultSet checkResult = checkStmt.executeQuery();
			checkResult.next();
			int counter = checkResult.getInt(1);
			if(counter == 0){
				System.out.println("User "+id);
				int col = 1;
				insertStatement.setInt(col++, id);
				insertStatement.setString(col++, name);
				insertStatement.setInt(col++, accesslevel);
				
				insertStatement.executeUpdate();
			} else {
				System.out.println("User upt "+id);
				int col = 1;
				updateStatement.setString(col++, name);
				updateStatement.setInt(col++, accesslevel);
				updateStatement.setInt(col++, id);
				
				updateStatement.executeUpdate();
			}
		}
		updateStatement.close();
		insertStatement.close();
		checkStmt.close();
	}
	
	public void userLoad() throws SQLException{
		users.clear();
		
		String sql = "SELECT id, name, accesslevel FROM users ORDER BY id";
		Statement selectStatement = con.createStatement();
		
		ResultSet results = selectStatement.executeQuery(sql);
		
		while(results.next()){
			int id = results.getInt("id");
			String name = results.getString("name");
			int accesslevel = results.getInt("accesslevel");
			User user = new User(id,name,accesslevel);
			users.add(user);
		}
		
		results.close();
		selectStatement.close();
	}
	
	public void addUser(User user){
		users.add(user);
	}
	
	public void removeUser(int index){
		users.remove(index);
	}
	
	public List<User> getUsers(){
		return Collections.unmodifiableList(users);
	}
}
