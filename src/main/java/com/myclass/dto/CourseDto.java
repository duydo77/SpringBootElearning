package com.myclass.dto;

import java.util.Date;

public class CourseDto {

	private int id;

	private String title;

	private String image;

	private String hourCount;

	private String viewCount;

	private int price;

	private String discount;

	private String promotionPrice;

	private String desc;

	private String content;

	private int cateId;

	private Date lastUpdate;

	private String cateName;

	private String cateIcon;

	public CourseDto() {
	}

	public CourseDto(String title, String image, String hourCount, String viewCount, int price, String discount,
			String promotionPrice, String desc, String content, int cateId, Date lastUpdate, String cateName,
			String icon) {
		this.title = title;
		this.image = image;
		this.hourCount = hourCount;
		this.viewCount = viewCount;
		this.price = price;
		this.discount = discount;
		this.promotionPrice = promotionPrice;
		this.desc = desc;
		this.content = content;
		this.cateId = cateId;
		this.lastUpdate = lastUpdate;
		this.cateName = cateName;
		this.cateIcon = icon;
	}

	// int, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	// int, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	// int, java.util.Date, java.lang.String, java.lang.Strings
	public CourseDto(int id, String title, String image, String hourCount, String viewCount, int price, String discount,
			String promotionPrice, String desc, String content, int cateId, Date lastUpdate, String cateName,
			String icon) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.hourCount = hourCount;
		this.viewCount = viewCount;
		this.price = price;
		this.discount = discount;
		this.promotionPrice = promotionPrice;
		this.desc = desc;
		this.content = content;
		this.cateId = cateId;
		this.lastUpdate = lastUpdate;
		this.cateName = cateName;
		this.cateIcon = icon;
	}

	public CourseDto(int id, String title, String image, String hourCount, String viewCount, int price, String discount,
			String promotionPrice, String desc, String content, int cateId, Date lastUpdate) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.hourCount = hourCount;
		this.viewCount = viewCount;
		this.price = price;
		this.discount = discount;
		this.promotionPrice = promotionPrice;
		this.desc = desc;
		this.content = content;
		this.cateId = cateId;
		this.lastUpdate = lastUpdate;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getHourCount() {
		return hourCount;
	}

	public void setHourCount(String hourCount) {
		this.hourCount = hourCount;
	}

	public String getViewCount() {
		return viewCount;
	}

	public void setViewCount(String viewCount) {
		this.viewCount = viewCount;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDiscount() {
		return discount;
	}

	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
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
		return cateId;
	}

	public void setCateId(int cateId) {
		this.cateId = cateId;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getCateName() {
		return cateName;
	}

	public void setCateName(String cateName) {
		this.cateName = cateName;
	}

	public String getCateIcon() {
		return cateIcon;
	}

	public void setCateIcon(String cateIcon) {
		this.cateIcon = cateIcon;
	}

}
