package com.myclass.dto;

public class UploadVideoDto {

	private String title;
	private String videoAsBase64;
	
	public UploadVideoDto() {
	}
	
	public UploadVideoDto(String title, String videoAsBase64) {
		super();
		this.title = title;
		this.videoAsBase64 = videoAsBase64;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideoAsBase64() {
		return videoAsBase64;
	}
	public void setVideoAsBase64(String videoAsBase64) {
		this.videoAsBase64 = videoAsBase64;
	}
	
}
