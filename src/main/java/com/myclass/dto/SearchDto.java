package com.myclass.dto;

import java.util.Map;

public class SearchDto {
	
//	  private Map<String, String[]> map;
//	  
//	  public SearchDto() { }
//	  
//	  public SearchDto(Map<String, String[]> map) { this.map = map; }
//	  
//	  public Map<String, String[]> getMap() { return map; }
//	  
//	  public void setMap(Map<String, String[]> map) { this.map = map; }
	 
	private String key;
	private String p;
	private String[] category;
	private String[] duration;
	private String[] price;
	
	public SearchDto(String key, String p, String[] category, String[] duration, String[] price) {
		super();
		this.key = key;
		this.p = p;
		this.category = category;
		this.duration = duration;
		this.price = price;
	}

	public SearchDto() {
		super();
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public String[] getDuration() {
		return duration;
	}

	public void setDuration(String[] duration) {
		this.duration = duration;
	}

	public String[] getPrice() {
		return price;
	}

	public void setPrice(String[] price) {
		this.price = price;
	}
	
	
	
}
