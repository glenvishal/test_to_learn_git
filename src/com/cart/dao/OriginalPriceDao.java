package com.cart.dao;

public interface OriginalPriceDao {
	public void setPrice(String sku, String name, double price);
	public double getPrice(String sku);

}
