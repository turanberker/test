package com.berker.servicebase.dto;

import java.util.Date;

public class ExceptionResponseModel {
	
	private String message;
	
	private Date timeStamp;

	public ExceptionResponseModel(String message, Date timeStamp) {
		super();
		this.message = message;
		this.timeStamp = timeStamp;
	}

	protected ExceptionResponseModel() {
		super();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	

}
