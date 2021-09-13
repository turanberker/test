package com.orderManagement.orderManagement.data;

public enum OrderStatus {

	
	NEW(0),
	SENT(1),
	COMPLETED(2),
	CANCELLED(3),
	;
	
	private final int value;
	
	OrderStatus(int value){
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
}

