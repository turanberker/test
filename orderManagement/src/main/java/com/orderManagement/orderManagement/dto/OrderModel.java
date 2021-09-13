package com.orderManagement.orderManagement.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.orderManagement.orderManagement.constraints.OrderConstraints;
import com.orderManagement.orderManagement.data.OrderStatus;

public class OrderModel {

	@NotNull
	@Positive
	private Long id;

	@NotNull
	@Size(min = OrderConstraints.DETAIL_STRING_MIN, max = OrderConstraints.DETAIL_STRING_MAX)
	private String address;

	@NotNull
	private OrderStatus orderStatus = OrderStatus.NEW;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	
	
}
