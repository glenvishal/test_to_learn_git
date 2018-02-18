package com.cart.rules;

public class BulkDiscountRule implements Rules{
	private String rule;
	private String sku;
	private int numOfItem;
	private double newPrice;
	
	public BulkDiscountRule(String rule, String sku,int numOfItem, double newPrice){
		this.rule = rule;
		this.sku = sku;
		this.numOfItem = numOfItem;
		this.newPrice = newPrice;
	}

	@Override
	public String getRule() {
		return rule;
	}
	
	public double getNewPrice(){
		return newPrice;
	}
	
	public int getNumOfItem(){
		return numOfItem;
	}

}
