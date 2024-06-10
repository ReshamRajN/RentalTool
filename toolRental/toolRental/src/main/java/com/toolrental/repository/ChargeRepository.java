package com.toolrental.repository;

import com.toolrental.exception.InvalidChargeException;
import com.toolrental.model.Charge;

public interface ChargeRepository {
	public Charge getChargeByToolType(String toolType) throws InvalidChargeException;
}
