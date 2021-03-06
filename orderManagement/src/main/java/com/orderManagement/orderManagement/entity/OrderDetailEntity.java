package com.orderManagement.orderManagement.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.berker.servicebase.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.orderManagement.orderManagement.constraints.OrderDetailConstraints;
import com.orderManagement.orderManagement.dto.OmProductModel;

@Entity
@Table(name = OrderDetailConstraints.TABLE_NALE)
public class OrderDetailEntity extends BaseEntity {

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORDER_ID", updatable = false)
	private OrderEntity order;

	@NotNull
	@Column(name = "PRODUCT_ID", updatable = false)
	private Long productId;

	@NotNull
	@Size(max = OrderDetailConstraints.NAME_MAX)
	private String categoryName;

	@NotNull
	@Size(max = OrderDetailConstraints.NAME_MAX)
	private String produtName;

	@NotNull
	@Min(0)
	private Integer quantity;

	@NotNull
	private BigDecimal unitPrice;

	public OrderDetailEntity(OmProductModel productModel) {
		this.productId = productModel.getId();
		this.categoryName = productModel.getCategory().getName();
		this.produtName = productModel.getName();
		this.unitPrice=productModel.getPrice();
	}

	protected OrderDetailEntity() {
		super();
	}

	public OrderEntity getOrder() {
		return order;
	}

	public void setOrder(OrderEntity order) {
		this.order = order;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getProdutName() {
		return produtName;
	}

	public void setProdutName(String produtName) {
		this.produtName = produtName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
