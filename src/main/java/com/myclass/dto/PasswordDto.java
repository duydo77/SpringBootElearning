package com.myclass.dto;

public class PasswordDto {

	private String email;
	private String newPassword;
	private String oldPassword;

	public PasswordDto() {

	}

	public PasswordDto(String email, String newPassword, String oldPassword) {
		this.email = email;
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}
	 // dat
	public PasswordDto( String newPassword, String oldPassword) {
		this.newPassword = newPassword;
		this.oldPassword = oldPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

}
