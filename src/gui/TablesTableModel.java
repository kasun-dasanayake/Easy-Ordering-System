package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Table;

public class TablesTableModel extends AbstractTableModel {
	
	private List<Table> db;
	
	private String[] colNames =  {"Name","Available"};
	
	public TablesTableModel() {
	}
	
	public void setData(List<Table> db) {
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
		Table table = db.get(row);
		switch(col){
		case 0:
			return table.getName();
		case 1:
			return table.getAvailable();
		}
		return null;
	}

}
