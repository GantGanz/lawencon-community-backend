package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.authentication.BadCredentialsException;
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
	public ResponseEntity<ErrorRes<String>> handleVerification(final InvalidVerificationCodeException ex) {
		final String error = ex.getMessage();
		final ErrorRes<String> errorRes = new ErrorRes<>();
		errorRes.setMessage(error);
		ex.printStackTrace();
		return new ResponseEntity<>(errorRes, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorRes<String>> handleCredentials(final BadCredentialsException ex) {
		final String errors = "Wrong UserName or Password";
		final ErrorRes<String> errorResDto = new ErrorRes<>();
		errorResDto.setMessage(errors);
		return new ResponseEntity<>(errorResDto, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ErrorRes<String>> handleObjectOptimistic(
			final ObjectOptimisticLockingFailureException ex) {
		final String error = "Version didn't match, please refresh and try again";
		final ErrorRes<String> errorResDto = new ErrorRes<>();
		errorResDto.setMessage(error);
		return new ResponseEntity<>(errorResDto, HttpStatus.BAD_REQUEST);
	}
}
