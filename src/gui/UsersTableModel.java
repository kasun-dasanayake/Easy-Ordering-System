package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.User;

public class UsersTableModel extends AbstractTableModel {
	
	private List<User> db;
	
	private String[] colNames =  {"Name","Access Level"};
	
	public UsersTableModel() {
	}
	
	public void setData(List<User> db) {
		this.db = db;
	}
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		User user = db.get(row);
		switch(col){
		case 0:
			return user.getName();
		case 1:
			return user.getAccessLevel();
		}
		return null;
	}

}
