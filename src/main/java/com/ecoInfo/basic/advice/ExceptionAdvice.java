package com.ecoInfo.basic.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.ecoInfo.basic.util.CustomException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ExceptionAdvice extends ResponseEntityExceptionHandler {

	@ExceptionHandler(CustomException.class)
	ResponseEntity<String> handleException(CustomException exception, HttpServletRequest req, HttpServletResponse res) {
		return new ResponseEntity<>(exception.getMessage(), exception.getHttpStatus());
	}

}
