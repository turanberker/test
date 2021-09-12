package com.pim.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.berker.servicebase.entity.BaseEntity;
import com.pim.constraints.ProductConstraints;




@Entity
@Table(name=ProductConstraints.TABLE_NAME)
public class ProductEntity extends BaseEntity {

	@NotNull
	@Column(name="NAME" ,nullable=false)
	@Size(max=ProductConstraints.NAME_MAX)
	private String name;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="CATEGORY_ID",foreignKey=@ForeignKey(name="FK_PRODUCT_CAT0"))
	private CategoryEntity category;
	
	@Min(0)
	@Column(name="PRICE")
	private BigDecimal price;
	
	@Min(0)
	@Column(name="STOCK_QUANTITY")
	private Integer stockQuantity;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
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
