package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Table;

public class TablesTable extends JPanel {
	
	private JTable table;
	private TablesTableModel tableModel;

	public TablesTable() {
		tableModel = new TablesTableModel();
		table = new JTable(tableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
	}
	
	public void setData(List<Table> db){
		tableModel.setData(db);
	}
	
	public void refresh(){
		tableModel.fireTableDataChanged();
	}
}
