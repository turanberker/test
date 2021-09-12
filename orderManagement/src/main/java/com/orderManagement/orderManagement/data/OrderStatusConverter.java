package com.orderManagement.orderManagement.data;

import javax.persistence.AttributeConverter;

public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

	public Integer convertToDatabaseColumn(OrderStatus attribute) {
		return attribute.getValue();
	}

	public OrderStatus convertToEntityAttribute(Integer dbData) {
		if (dbData == null) {
			return null;
		} else {
			for (OrderStatus status : OrderStatus.values()) {
				if (dbData.equals(status.getValue())) {
					return status;
				}
			}
		}
		return null;
	}

}
