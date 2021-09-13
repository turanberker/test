package com.orderManagement.orderManagement.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.orderManagement.orderManagement.constraints.OrderConstraints;

public class ChangeAddressModel {

	@NotNull
	@Positive
	private Long orderId;
	
	@NotNull
	@NotBlank
	@Size(min = OrderConstraints.DETAIL_STRING_MIN, max = OrderConstraints.DETAIL_STRING_MAX)
	private String newAddress;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getNewAddress() {
		return newAddress;
	}

	public void setNewAddress(String newAddress) {
		this.newAddress = newAddress;
	}
	
	
}
