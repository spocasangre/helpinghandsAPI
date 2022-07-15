package com.taquitosncapas.helpinghands.models.dtos.auth;

public class MessageRegisterResponse {
	
	private String message;

	public MessageRegisterResponse() {
		super();
	}

	public MessageRegisterResponse(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
