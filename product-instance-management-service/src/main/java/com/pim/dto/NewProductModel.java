package com.pim.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.pim.constraints.ProductConstraints;

public class NewProductModel {
	//FIXME PIM COMMON Paketi yapılacak ve DTO Paketi oraya taşınacak. OrderManagement da pim-common 'a referans verecek
	
	@NotNull
	@Size(max = ProductConstraints.NAME_MAX)
	private String name;

	private Long categoryId;

	@Min(0)

	private BigDecimal price;

	@Min(0)

	private Integer stockQuantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

}
