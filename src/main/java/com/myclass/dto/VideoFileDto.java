package com.myclass.dto;

import org.springframework.core.io.ByteArrayResource;

public class VideoFileDto {

	private int length;
	private ByteArrayResource byteArrayResource;
	
	public VideoFileDto() {}
	
	public VideoFileDto(int length, ByteArrayResource byteArrayResource) {
		super();
		this.length = length;
		this.byteArrayResource = byteArrayResource;
	}
	
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public ByteArrayResource getByteArrayResource() {
		return byteArrayResource;
	}
	public void setByteArrayResource(ByteArrayResource byteArrayResource) {
		this.byteArrayResource = byteArrayResource;
	}
	
}
