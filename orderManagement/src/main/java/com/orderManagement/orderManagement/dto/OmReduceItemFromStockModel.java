package com.orderManagement.orderManagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OmReduceItemFromStockModel {

	@NotNull
	@Positive
	private Long productId;
	
	@NotNull
	@Positive
	private Integer quantityToReduce;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantityToReduce() {
		return quantityToReduce;
	}

	public void setQuantityToReduce(Integer quantityToReduce) {
		this.quantityToReduce = quantityToReduce;
	}
	
	
}
