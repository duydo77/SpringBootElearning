package com.myclass.dto;

public class UpdateProfileReponseDto {
	private String message;
	private String token;

	public UpdateProfileReponseDto() {
		super();
	}

	public UpdateProfileReponseDto(String message, String token) {
		super();
		this.message = message;
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
