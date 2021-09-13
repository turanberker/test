package com.orderManagement.orderManagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class AddNewItemModel {

	@NotNull
	@Positive
	private Long orderId;
	
	@NotNull
	private NewOrderDetailModel newItem;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public NewOrderDetailModel getNewItem() {
		return newItem;
	}

	public void setNewItem(NewOrderDetailModel newItem) {
		this.newItem = newItem;
	}
	
	
	
}
