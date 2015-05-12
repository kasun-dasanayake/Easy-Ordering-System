package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Recipe;

public class RecipesTable extends JPanel {
	
	private JTable table;
	private RecipesTableModel tableModel;

	public RecipesTable() {
		tableModel = new RecipesTableModel();
		table = new JTable(tableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
	}
	
	public void setData(List<Recipe> db){
		tableModel.setData(db);
	}
	
	public void refresh(){
		tableModel.fireTableDataChanged();
	}
}
