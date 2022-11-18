package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.lawencon.community.dto.error.ErrorRes;
import com.lawencon.util.VerificationCodeUtil.InvalidVerificationCodeException;

@ControllerAdvice
public class ErrorHandler {
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorRes<String>> handleRuntime(final RuntimeException ex) {
		final String error = ex.getMessage();
		final ErrorRes<String> errorRes = new ErrorRes<>();
		errorRes.setMessage(error);
		ex.printStackTrace();
		return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidVerificationCodeException.class)
	public ResponseEntity<ErrorRes<String>> handleVerification(final InvalidVerificationCodeException ex){ 
		final String error = ex.getMessage();
		final ErrorRes<String> errorRes = new ErrorRes<>();
		errorRes.setMessage(error);
		ex.printStackTrace();
		return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
	}
}
