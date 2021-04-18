package com.myclass.dto;

public class UserUpdateDto {
	private String email;

	private String fullname;
	
	private String phone;

	private String address;

	public UserUpdateDto() {
		super();
	}

	public UserUpdateDto(String email, String fullname, String phone, String address) {
		this.email = email;
		this.fullname = fullname;
		this.phone = phone;
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
