package com.cart.rules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PricingRule {
	private HashMap<String, Rules> ruleMap = 
			new HashMap<String,Rules>();
	
	public void setNewRule(String whichRule, String... args){
		if(whichRule.equals(DefineRules.OFFER)){
			OfferRule rule = new OfferRule(whichRule, args[0], 
					Integer.parseInt(args[1]), Double.parseDouble(args[2]));
			ruleMap.put(args[0], rule);			
		}		
		else if(whichRule.equals(DefineRules.BULK_DISCOUNT)){
			BulkDiscountRule rule = new BulkDiscountRule(whichRule, args[0], 
					Integer.parseInt(args[1]), Double.parseDouble(args[2]));
			ruleMap.put(args[0], rule);			
		}
		
		else if(whichRule.equals(DefineRules.ITEM_FREE)){
			List<String> freeItemList = new ArrayList<String>(); 
			
			for(int i=1; i < args.length; i++){
				freeItemList.add(args[i]);
			}
			FreeItemRule rule = new FreeItemRule(whichRule, args[0], freeItemList);
			ruleMap.put(args[0], rule);			
		}
	}
	
	public Rules getRules(String sku){
		return ruleMap.get(sku);
	}

}
