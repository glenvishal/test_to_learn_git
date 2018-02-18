package com.cart.checkout;

import java.util.HashMap;
import java.util.List;

import com.cart.dao.OriginalPriceDao;
import com.cart.dao.OriginalPriceDaoImpl;
import com.cart.rules.BulkDiscountRule;
import com.cart.rules.DefineRules;
import com.cart.rules.FreeItemRule;
import com.cart.rules.OfferRule;
import com.cart.rules.PricingRule;
import com.cart.rules.Rules;

public class Checkout {
	private PricingRule pricingRule;
	private OriginalPriceDao originalPriceDao = OriginalPriceDaoImpl.getInstance();
	private double total = 0.0;
	private HashMap<String, Integer> tempBasket = new HashMap<String, Integer>();
	
	public Checkout(PricingRule pricingRule){
		this.pricingRule = pricingRule;
	}
	
	public void scan(String sku){
		double price = originalPriceDao.getPrice(sku);		
		total += price;		
		if(tempBasket.get(sku) == null){
			tempBasket.put(sku, 1);
		}
		else{
			tempBasket.put(sku, tempBasket.get(sku) + 1);
		}
		
		if(pricingRule.getRules(sku) != null){
			Rules rules = pricingRule.getRules(sku);
			
			if(rules.getRule().equals(DefineRules.OFFER)){
				OfferRule offerRule = (OfferRule) rules;
				if(tempBasket.get(sku) % offerRule.getNumOfItem() == 0){
					total -= getOfferPrice(offerRule, sku);
				}				
			}
			else if(rules.getRule().equals(DefineRules.BULK_DISCOUNT)){
				BulkDiscountRule bulkDisrule = (BulkDiscountRule) rules;
				if(tempBasket.get(sku) == bulkDisrule.getNumOfItem()){
					total -= getBulkDiscountPrice(bulkDisrule, sku, tempBasket.get(sku));
				}
				else if(tempBasket.get(sku) > bulkDisrule.getNumOfItem()){
					total -= (originalPriceDao.getPrice(sku) - bulkDisrule.getNewPrice());
				}
			}
			else if(rules.getRule().equals(DefineRules.ITEM_FREE)){
				FreeItemRule freeItemRule = (FreeItemRule) rules;
				List<String> freeItemSku = freeItemRule.getFreeItemSku();
				
				for(String eachSku: freeItemSku){
					if(tempBasket.get(eachSku) == null){
						tempBasket.put(eachSku, 1);
					}
					else if(tempBasket.get(eachSku) >= 1){
						total -= originalPriceDao.getPrice(eachSku);
					}
				}				
			}
		}		
	}
	
	public double total(){
		System.out.println("Products in basket: "+tempBasket);
		System.out.println("Total: "+total);
		return this.total;
	}
	
	private double getOfferPrice(OfferRule offerRule, String sku){
		double tempPrice = (originalPriceDao.getPrice(sku) * offerRule.getNumOfItem())-
				offerRule.getNewPrice();
		return tempPrice;
	}
	
	private double getBulkDiscountPrice(BulkDiscountRule bulkDisrule, 
			String sku, int itemsInBasket){
		double tempPrice = (originalPriceDao.getPrice(sku)  - bulkDisrule.getNewPrice())
				* itemsInBasket;
		return tempPrice;
	}

}
