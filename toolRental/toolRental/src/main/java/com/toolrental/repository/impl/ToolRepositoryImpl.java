package com.toolrental.repository.impl;

import java.util.HashMap;
import java.util.Map;

import com.toolrental.exception.InvalidToolException;
import com.toolrental.model.Tool;
import com.toolrental.repository.ToolRepository;

public class ToolRepositoryImpl implements ToolRepository {

	private static Map<String, Tool> tools = new HashMap<String, Tool>() {
		{
			put("CHNS", new Tool("CHNS", "Chainsaw", "Stihl"));
			put("LADW", new Tool("LADW", "Ladder", "Werner"));
			put("JAKD", new Tool("JAKD", "Jackhammer", "DeWalt"));
			put("JAKR", new Tool("JAKR", "Jackhammer", "Ridgid"));
		}
	};

	public Tool getToolByCode(String toolCode) throws InvalidToolException {
		if (tools.containsKey(toolCode)) {
			return tools.get(toolCode);
		} else {
			throw new InvalidToolException(String.format("No tool found with code: %s", toolCode));
		}
	}

}
