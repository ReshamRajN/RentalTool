package com.toolrental;

import com.toolrental.service.impl.CheckoutServiceImpl;

public class ToolRentalApplication {

	public static void main(String[] args) throws Exception {

		CheckoutServiceImpl checkoutService = new CheckoutServiceImpl();

		System.out.println(checkoutService.checkout("JAKR", "9/3/15", 5, "101%"));
		System.out.println("---------------------------------------------------");
		System.out.println(checkoutService.checkout("LADW", "7/2/20", 3, "10%"));
		System.out.println("---------------------------------------------------");
		System.out.println(checkoutService.checkout("CHNS", "7/2/15", 5, "25%"));
		System.out.println("---------------------------------------------------");
		System.out.println(checkoutService.checkout("JAKD", "9/3/15", 6, "0%"));
		System.out.println("---------------------------------------------------");
		System.out.println(checkoutService.checkout("JAKR", "7/2/15", 9, "0%"));
		System.out.println("---------------------------------------------------");
		System.out.println(checkoutService.checkout("JAKR", "7/2/20", 4, "50%"));
		System.out.println("---------------------------------------------------");

	}
}
