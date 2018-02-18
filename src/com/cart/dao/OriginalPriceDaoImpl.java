package com.cart.dao;

import java.util.HashMap;

import com.cart.entity.OriginalPriceEntity;

public class OriginalPriceDaoImpl implements OriginalPriceDao {
	/*
	 * Using originalPriceMap as temp db to store and retrive 
	 * price of the products
	 */
	private HashMap<String, OriginalPriceEntity> originalPriceMap =
			new HashMap<String, OriginalPriceEntity>();
	private static OriginalPriceDaoImpl myObj;
	
	private OriginalPriceDaoImpl(){
		
	}
	
	public static OriginalPriceDaoImpl getInstance(){
        if(myObj == null){
            myObj = new OriginalPriceDaoImpl();
        }
        return myObj;
    }

	@Override
	public void setPrice(String sku, String name, double price) {
		OriginalPriceEntity originalPriceEntity = 
				new OriginalPriceEntity(sku, name, price);
		System.out.println("new Product inserted - "+originalPriceEntity);
		originalPriceMap.put(sku, originalPriceEntity);
	}

	@Override
	public double getPrice(String sku) {
		OriginalPriceEntity originalPriceEntity = 
				originalPriceMap.get(sku);
		return originalPriceEntity.getPrice();
	}

}
