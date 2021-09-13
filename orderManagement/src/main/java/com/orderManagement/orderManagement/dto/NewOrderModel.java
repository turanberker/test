package com.orderManagement.orderManagement.dto;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.orderManagement.orderManagement.constraints.OrderConstraints;

public class NewOrderModel {
	
	@NotNull
	@NotBlank
	@Size(min = OrderConstraints.DETAIL_STRING_MIN, max = OrderConstraints.DETAIL_STRING_MAX)
	private String address;
	
	@NotEmpty
	private List<NewOrderDetailModel> orderDetailList;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<NewOrderDetailModel> getOrderDetailList() {
		return orderDetailList;
	}

	public void setOrderDetailList(List<NewOrderDetailModel> orderDetailList) {
		this.orderDetailList = orderDetailList;
	}
	
	
}
