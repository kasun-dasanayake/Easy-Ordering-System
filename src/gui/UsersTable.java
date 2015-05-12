package gui;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.User;

public class UsersTable extends JPanel {
	
	private JTable table;
	private UsersTableModel tableModel;

	public UsersTable() {
		tableModel = new UsersTableModel();
		table = new JTable(tableModel);
		
		setLayout(new BorderLayout());
		add(new JScrollPane(table),BorderLayout.CENTER);
	}
	
	public void setData(List<User> db){
		tableModel.setData(db);
	}
	
	public void refresh(){
		tableModel.fireTableDataChanged();
	}
}
