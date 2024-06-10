package com.toolrental.repository;

import com.toolrental.exception.InvalidToolException;
import com.toolrental.model.Tool;

public interface ToolRepository {
	public Tool getToolByCode(String toolCode) throws InvalidToolException;
}
