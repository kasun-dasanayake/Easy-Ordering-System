package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
	
	private static final long serialVersionUID = -9147871717786825391L;
	private static int cnt = 1; 
	private int id;
	private String buyer;
	private String count;
	private State stateCategory;
	private Type type;
	private String coupon;
	private boolean couponCheck;
	private Pay payCommand;
	private ShoppingCart shoppingCart;
	
	public Order(String buyer,String count,State stateCategory,Type type,
			String coupon,boolean couponCheck,Pay payCommand){
		this.buyer = buyer;
		this.count = count;
		this.stateCategory = stateCategory;
		this.type = type;
		this.coupon = coupon;
		this.couponCheck = couponCheck;
		this.payCommand = payCommand;
		
		this.id = cnt;
		cnt++;
	}
	
	public Order(int id,String buyer,String count,State stateCategory,Type type,
			String coupon,boolean couponCheck,Pay payCommand){
		this(buyer,count,stateCategory,type,coupon,couponCheck,payCommand);
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBuyer() {
		return buyer;
	}
	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public State getStateCategory() {
		return stateCategory;
	}
	
	public void setStateCategory(State stateCategory) {
		this.stateCategory = stateCategory;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public String getCoupon() {
		return coupon;
	}
	
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	
	public boolean isCouponCheck() {
		return couponCheck;
	}
	
	public void setCouponCheck(boolean couponCheck) {
		this.couponCheck = couponCheck;
	}
	
	public Pay getPayCommand() {
		return payCommand;
	}
	
	public void setPayCommand(Pay payCommand) {
		this.payCommand = payCommand;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", buyer=" + buyer + ", count=" + count
				+ ", stateCategory=" + stateCategory + ", type=" + type
				+ ", coupon=" + coupon + ", couponCheck=" + couponCheck
				+ ", payCommand=" + payCommand + "]";
	}
    
	
}
