package com.berker.servicebase.configuration;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.berker.servicebase.dto.ExceptionResponseModel;
import com.berker.servicebase.exceptions.BaseException;
import com.berker.servicebase.exceptions.DataNotFoundException;

public class BaseRestController   {

	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Object> handleExceptions(BaseException ex, WebRequest webRequest) {
		ExceptionResponseModel response = new ExceptionResponseModel(ex.getMessage(), new Date());

		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
		
	}

	

}
