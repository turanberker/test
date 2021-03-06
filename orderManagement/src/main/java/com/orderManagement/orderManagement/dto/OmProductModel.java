package com.orderManagement.orderManagement.dto;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class OmProductModel {
	//FIXME PIM Common yapıldıktan sonra bu model silenecek ve commondaki classlar kullanılacak
	
	private Long id;
	
	@NotNull	
	private String name;
	
	@NotNull
	private OmCategoryModel category;
	
	@Min(0)
	private BigDecimal price;
	
	@Min(0)
	private Integer stockQuantity;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public OmCategoryModel getCategory() {
		return category;
	}

	public void setCategory(OmCategoryModel category) {
		this.category = category;
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
