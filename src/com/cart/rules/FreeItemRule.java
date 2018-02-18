package com.cart.rules;

import java.util.ArrayList;
import java.util.List;

public class FreeItemRule implements Rules {
	
	private String rule;
	private String sku;
	private List<String> freeItemSku;
	
	public FreeItemRule(String rule, String sku, List<String> freeItemSku){
		this.freeItemSku = freeItemSku;
		this.sku = sku;
		this.rule = rule;
	}

	@Override
	public String getRule() {
		return rule;
	}
	
	public List<String> getFreeItemSku(){
		return freeItemSku;
	}

}
