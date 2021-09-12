package com.berker.servicebase.dto;

import javax.validation.constraints.Positive;

public class BaseModel {

	@Positive
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
