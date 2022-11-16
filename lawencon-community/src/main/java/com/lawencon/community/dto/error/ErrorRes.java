package com.lawencon.community.dto.error;

public class ErrorRes<T> {
	private T message;

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}
}
