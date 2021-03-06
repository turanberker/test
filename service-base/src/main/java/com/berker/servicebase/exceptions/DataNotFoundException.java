package com.berker.servicebase.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DataNotFoundException extends BaseException {
	
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String className, String message) {
		super(message, className);
	}

	protected DataNotFoundException() {
		// kullanılmasın diye yazılmıştır
	}

}
