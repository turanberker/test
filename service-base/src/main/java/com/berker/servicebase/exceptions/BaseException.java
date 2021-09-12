package com.berker.servicebase.exceptions;

import java.util.Date;

public class BaseException extends RuntimeException {
	
	private Date timeStamp;

	private String className;

	
	public BaseException() {
		super();
	}

	public BaseException(String message, String className) {
		super(message);
		this.timeStamp = new Date();
		this.className = className;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	

}
