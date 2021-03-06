package com.orderManagement.orderManagement.exceptions;

import java.text.MessageFormat;

import com.berker.servicebase.exceptions.BaseException;
import com.orderManagement.orderManagement.data.OrderStatus;

public class OrderCanNotEdittedException extends BaseException {
	
	private Long orderId;

	private OrderStatus status;

	protected OrderCanNotEdittedException() {

	}

	public OrderCanNotEdittedException(Long orderId, OrderStatus status, String className) {
		super(MessageFormat.format("Order with Id {0} status is {1} so it can not be changed", orderId, status.name()),
				className);

		this.orderId = orderId;
		this.status = status;
	}

}
