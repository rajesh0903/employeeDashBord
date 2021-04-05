package com.rky.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import com.rky.exception.CustomException;
import com.rky.exception.ErrorDetails;

@RestControllerAdvice
public class ControllerAdvice {	
	
	@ExceptionHandler(CustomException.class)
	public ResponseEntity<?> useException(CustomException ex) {		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setCode(HttpStatus.NOT_FOUND.value());
		errorDetails.setDate(new Date());
		errorDetails.setMessage(ex.getMessage());
		
		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	}
	//Global Exception	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> globalException(Exception ex, WebRequest req) {		
		ErrorDetails errorDetails = new ErrorDetails();
		errorDetails.setCode(HttpStatus.NOT_FOUND.value());
		errorDetails.setDate(new Date());
		errorDetails.setMessage(ex.getMessage());

		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	}

}
