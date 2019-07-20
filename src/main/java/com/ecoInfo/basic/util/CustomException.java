package com.ecoInfo.basic.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = -2867874161893580106L;
	
	@Getter
	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public CustomException() {
		super();
	}
	
	public CustomException(String message) {
		super(message);
	}

	public CustomException(HttpStatus httpStatus) {
		super(httpStatus.toString());
		this.httpStatus = httpStatus;
	}

	public CustomException(HttpStatus httpStatus, String message) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public CustomException(HttpStatus httpStatus, String message, Throwable throwable) {
		super(message, throwable);
		this.httpStatus = httpStatus;
	}

}
