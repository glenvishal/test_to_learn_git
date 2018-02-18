package com.cart.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.cart.checkout.Checkout;
import com.cart.dao.OriginalPriceDao;
import com.cart.dao.OriginalPriceDaoImpl;
import com.cart.rules.DefineRules;
import com.cart.rules.PricingRule;

public class ShoppingCartTest {
	OriginalPriceDao originalPrice;
	PricingRule pricingRule;
	
	@Before
	public void init(){
		originalPrice = OriginalPriceDaoImpl.getInstance();
		originalPrice.setPrice("ipd", "Super iPad", 549.99);
		originalPrice.setPrice("mbp", "MacBook Pro", 1399.99);
		originalPrice.setPrice("atv", "Apple TV", 109.50);
		originalPrice.setPrice("vga", "VGA adapter", 30.00);
		
		pricingRule = new PricingRule();
		/*
		 * 3 tv for price of 2, Parameter 1: define the rule
		 * Para 2: sku
		 * Para 3: number of item
		 * Para 4: new price
		 */
		pricingRule.setNewRule(DefineRules.OFFER, "atv", "3", "219");
		
		/*
		 * bulk discount, Parameter 1: define the rule
		 * Para 2: sku
		 * Para 3: Minimum number of items for bulk discount
		 * Para 4: new price
		 */
		pricingRule.setNewRule(DefineRules.BULK_DISCOUNT, "ipd", "5", "499.99");
		
		/*
		 * free item, Parameter 1: define the rule
		 * Para 2: sku
		 * Para 3: list of free item sku
		 */
		pricingRule.setNewRule(DefineRules.ITEM_FREE, "mbp", "vga");
	}

//	@Test
//	public void testOriginalPriceDaoImpl() {
//		
//		assertEquals(originalPrice.getPrice("ipd"), 549.99, 1);		
//		assertEquals(originalPrice.getPrice("mbp"), 1399.99, 1);		
//		assertEquals(originalPrice.getPrice("atv"), 109.50, 1);		
//		assertEquals(originalPrice.getPrice("vga"), 30.00, 1);
//	}
	
	@Test
	public void testCheckoutTv() {		
		Checkout checkout = new Checkout(pricingRule);
		checkout.scan("atv");
		assertEquals(checkout.total(), 109.50, 1);
		
		checkout.scan("atv");
		assertEquals(checkout.total(), 219, 1);
		
		checkout.scan("atv");
		assertEquals(checkout.total(), 219, 1);
		
		checkout.scan("atv");
		assertEquals(checkout.total(), 328.5, 1);
		
		checkout.scan("atv");
		assertEquals(checkout.total(), 438.0, 1);
		
		checkout.scan("atv");
		assertEquals(checkout.total(), 438.0, 1);
	}
	
	@Test
	public void testCheckoutBulkDiscount() {		
		Checkout checkout = new Checkout(pricingRule);
		checkout.scan("ipd");
		assertEquals(checkout.total(), 549.99, 1);
		
		checkout.scan("ipd");
		assertEquals(checkout.total(), 1099.98, 1);
		
		checkout.scan("ipd");
		assertEquals(checkout.total(), 1649.97, 1);
		
		checkout.scan("ipd");
		assertEquals(checkout.total(), 2199.96, 1);
		
		checkout.scan("ipd");
		assertEquals(checkout.total(), 2499.95, 1);
		
		checkout.scan("ipd");
		assertEquals(checkout.total(), 2999.94, 1);		
	}
	
	@Test
	public void testCheckoutMacbookPro() {		
		Checkout checkout = new Checkout(pricingRule);
		checkout.scan("mbp");
		assertEquals(checkout.total(), 1399.99 , 1);
	}

}
