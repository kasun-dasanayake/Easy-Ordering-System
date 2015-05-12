package model;

public class User {

	private static int userCount = 1;
	private int id;
	private String name;
	private int accessLevel;
	
	public User(String name,int accessLevel) {
		this.name = name;
		this.accessLevel = accessLevel;
		
		this.id = userCount;
		userCount++;
	}
	
	public User(int id, String name, int accessLevel) {
		this(name, accessLevel);
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

	public int getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(int accessLevel) {
		this.accessLevel = accessLevel;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", accessLevel="
				+ accessLevel + "]";
	}

}
