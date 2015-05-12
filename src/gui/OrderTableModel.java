package gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.Order;

public class OrderTableModel extends AbstractTableModel {

	private List<Order> db;
	
	private String[] colNames =  {"Buyer","Count","State","Type","Coupon","Coupon check","Paid"};
	
	public OrderTableModel() {
	}
	
	public void setData(List<Order> db) {
		this.db = db;
	}
	
	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return db.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		Order order = db.get(row);
		switch(col){
		case 0:
			return order.getBuyer();
		case 1:
			return order.getCount();
		case 2:
			return order.getStateCategory();
		case 3:
			return order.getType();
		case 4:
			return order.getCoupon();
		case 5:
			return order.isCouponCheck();
		case 6:
			return order.getPayCommand();
		}
		return null;
	}

}
