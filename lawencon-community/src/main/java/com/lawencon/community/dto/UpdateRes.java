package com.lawencon.community.dto;

public class UpdateRes {

	private UpdateDataRes data;
	private String message;

	public UpdateDataRes getData() {
		return data;
	}

	public void setData(UpdateDataRes data) {
		this.data = data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
