package com.toolrental.service;

import com.toolrental.model.RentalAgreement;

public interface CheckoutService {
	RentalAgreement checkout(String toolCode, String checkout, int rentalDayCount, String discount);
}
