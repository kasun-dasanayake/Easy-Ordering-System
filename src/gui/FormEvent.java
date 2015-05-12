package gui;

import java.util.EventObject;

public class FormEvent extends EventObject{

	private String buyer;
	private String count;
	private int stateCategory;
	private String type;
	private String coupon;
	private boolean couponCheck;
	private String payCommand;
	
	public FormEvent(Object source) {
		super(source);
	}
	
	public FormEvent(Object source, String buyer, String count,int stateCategory,String type,
			String coupon,boolean couponCheck,String payCommand) {
		super(source);
		this.buyer = buyer;
		this.count = count;
		this.stateCategory = stateCategory;
		this.type = type;
		this.coupon = coupon;
		this.couponCheck = couponCheck;
		this.payCommand = payCommand;
	}

	public String getPayCommand() {
		return payCommand;
	}

	public String getCoupon() {
		return coupon;
	}

	public boolean isCouponCheck() {
		return couponCheck;
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

	public int getStateCategory() {
		return stateCategory;
	}

	public void setStateCategory(int stateCategory) {
		this.stateCategory = stateCategory;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
