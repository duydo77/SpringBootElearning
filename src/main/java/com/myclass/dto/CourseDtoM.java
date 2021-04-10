package com.myclass.dto;

public class CourseDtoM {
	private int id;
	private String title;
	private String desc;
	private String content;
	private int CateId;
	
	public CourseDtoM() {
		super();
	}
	
	public CourseDtoM(int id, String title, String desc, String content, int cateId) {
		super();
		this.id = id;
		this.title = title;
		this.desc = desc;
		this.content = content;
		CateId = cateId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getCateId() {
		return CateId;
	}
	public void setCateId(int cateId) {
		CateId = cateId;
	}
	
	

}
