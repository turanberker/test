package com.pim;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.berker.servicebase.dto.ExceptionResponseModel;
import com.berker.servicebase.exceptions.DataNotFoundException;


public class ExceptionHandle extends ResponseEntityExceptionHandler  {

//	@ExceptionHandler(value = { DataNotFoundException.class })
//	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(DataNotFoundException ex, HttpHeaders headers,
//			HttpStatus status, WebRequest request) {
//		ExceptionResponseModel response = new ExceptionResponseModel(ex.getMessage(), ex.getTimeStamp());
//		return new ResponseEntity(response, HttpStatus.NOT_FOUND);
//
//	}

	//@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<Object> handleExceptions(DataNotFoundException ex, WebRequest webRequest) {
		ExceptionResponseModel response = new ExceptionResponseModel(ex.getMessage(), new Date());

		ResponseEntity<Object> entity = new ResponseEntity(response, HttpStatus.NOT_FOUND);
		return entity;
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

}
