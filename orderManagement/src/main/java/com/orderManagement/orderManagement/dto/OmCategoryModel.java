package com.orderManagement.orderManagement.dto;

import com.berker.servicebase.dto.BaseModel;
import com.sun.istack.NotNull;

public class OmCategoryModel extends BaseModel {
	
	@NotNull
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
}
