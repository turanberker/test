package com.orderManagement.orderManagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class OmStockItemMovementModel {

	@NotNull
	@Positive
	private Long productId;

	@NotNull
	@Positive
	private Integer quantity;

	protected OmStockItemMovementModel() {
		super();
	}

	public OmStockItemMovementModel(@NotNull @Positive Long productId, @NotNull @Positive Integer quantity) {
		super();
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
