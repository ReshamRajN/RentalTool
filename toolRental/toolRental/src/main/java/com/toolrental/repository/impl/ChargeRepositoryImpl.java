package com.toolrental.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.toolrental.exception.InvalidChargeException;
import com.toolrental.model.Charge;
import com.toolrental.repository.ChargeRepository;

public class ChargeRepositoryImpl implements ChargeRepository {

	private Map<String, Charge> toolCharges = new HashMap<String, Charge>() {
		{
			put("Ladder", new Charge("Ladder", 1.99, true, true, false));
			put("Chainsaw", new Charge("Chainsaw", 1.49, true, false, true));
			put("Jackhammer", new Charge("Jackhammer", 2.99, true, false, false));
		}
	};

	public Charge getChargeByToolType(String toolType) throws InvalidChargeException {
		if (toolCharges.containsKey(toolType)) {
			return toolCharges.get(toolType);
		} else {
			System.out.println("No charge found for the tool type: " + toolType);
			throw new InvalidChargeException(String.format("No charge found for the tool type: {}", toolType));
		}
	}

}
