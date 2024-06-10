package com.toolrental.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.toolrental.model.RentalAgreement;
import com.toolrental.service.CheckoutService;
import com.toolrental.service.impl.CheckoutServiceImpl;

public class ToolRentalApplicationTest {
	
	public ToolRentalApplicationTest() {
	}
	
	private CheckoutService checkoutService = new CheckoutServiceImpl();
	
	@Test
	public void test1() {
		assertNull(checkoutService.checkout("JAKR", "9/3/15", 5, "101%"));
	}
	
	@Test
	public void test2() {
		RentalAgreement agreement = checkoutService.checkout("LADW", "7/2/20", 3, "10%");
		assertNotNull(agreement);
		assertEquals("$5.97", agreement.getPreDiscountCharge());
		assertEquals("$.60", agreement.getDiscountAmount());
		assertEquals("$5.37", agreement.getFinalCharge());
	}
	
	@Test
	public void test3() {
		RentalAgreement agreement = checkoutService.checkout("CHNS", "7/2/15", 5, "25%");
		assertNotNull(agreement);
		assertEquals("$2.98", agreement.getPreDiscountCharge());
		assertEquals("$.74", agreement.getDiscountAmount());
		assertEquals("$2.23", agreement.getFinalCharge());
	}
	
	@Test
	public void test4() {
		RentalAgreement agreement = checkoutService.checkout("JAKD", "9/3/15", 6, "0%");
		assertNotNull(agreement);
		assertEquals("$11.96", agreement.getPreDiscountCharge());
		assertEquals("$.00", agreement.getDiscountAmount());
		assertEquals("$11.96", agreement.getFinalCharge());
	}
	
	@Test
	public void test5() {
		RentalAgreement agreement = checkoutService.checkout("JAKR", "7/2/15", 9, "0%");
		assertNotNull(agreement);
		assertEquals("$17.94", agreement.getPreDiscountCharge());
		assertEquals("$.00", agreement.getDiscountAmount());
		assertEquals("$17.94", agreement.getFinalCharge());
	}
	
	@Test
	public void test6() {
		RentalAgreement agreement = checkoutService.checkout("JAKR", "7/2/20", 4, "50%");
		assertNotNull(agreement);
		assertEquals("$2.99", agreement.getPreDiscountCharge());
		assertEquals("$1.50", agreement.getDiscountAmount());
		assertEquals("$1.50", agreement.getFinalCharge());
	}
	
	@Test
	public void test7() {
		assertNull(checkoutService.checkout("JAKR", "9/3/15", -9, "10%"));
	}
	
	@Test
	public void test8() {
		assertNull(checkoutService.checkout("JAKR1", "9/3/15", 5, "1%"));
	}

}
