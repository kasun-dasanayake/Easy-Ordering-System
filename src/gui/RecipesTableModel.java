package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Recipe;

public class RecipesTableModel extends AbstractTableModel {
	
	private List<Recipe> db;
	
	private String[] colNames =  {"Name","Price"};
	
	public RecipesTableModel() {
	}
	
	public void setData(List<Recipe> db) {
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
		Recipe recipe = db.get(row);
		switch(col){
		case 0:
			return recipe.getName();
		case 1:
			return recipe.getPrice();
		}
		return null;
	}

}
