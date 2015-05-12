package model;

public class Table {
	
	private static int tableCount = 1;
	private int id;
	private String name;
	private String available;
	
	public Table(String name,String available) {
		this.name = name;
		this.available = available;
		
		this.id = tableCount;
		tableCount++;
	}
	
	public Table(int id, String name, String available) {
		this(name, available);
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

	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Override
	public String toString() {
		return "Table [id=" + id + ", name=" + name + ", available="
				+ available + "]";
	}

}
