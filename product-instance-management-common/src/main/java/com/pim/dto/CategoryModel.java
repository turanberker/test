package com.pim.dto;

import javax.validation.constraints.Size;

import com.pim.constraints.CategoryConstraints;
import com.sun.istack.NotNull;

public class CategoryModel {
	@NotNull
	@Size(max=CategoryConstraints.NAME_MAX)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
