package com.orderManagement.orderManagement.data;

public enum OrderStatus {

	
	NEW(0),
	SENT(1),
	RECEIVED(2),
	;
	
	private final int value;
	
	OrderStatus(int value){
		this.value=value;
	}

	public int getValue() {
		return value;
	}
	
}

