package com.myclass.dto;

public class RoleDto {

	private int id;

	private String name;

	private String desc;
	
	public RoleDto() {
	}

	public RoleDto(int id, String name, String desc) {
		super();
		this.id = id;
		this.name = name;
		this.desc = desc;
	}
	
	public RoleDto(String name, String disc) {
		this.name = name;
		this.desc = disc;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
}
