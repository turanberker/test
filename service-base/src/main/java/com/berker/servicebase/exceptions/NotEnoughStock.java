package com.berker.servicebase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotEnoughStock extends BaseException {
	
	private static final long serialVersionUID = 1L;
	
	protected NotEnoughStock() {

	}

	public NotEnoughStock(String message,String className) {
		super(message,className);
	}
}
