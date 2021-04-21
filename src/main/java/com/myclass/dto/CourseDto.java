package com.myclass.dto;

import java.util.Date;

public class CourseDto {

	private int id;

	private String title;

	private String image;

	private int lectureCount;

	private int hourCount;

	private int viewCount;

	private double price;

	private int discount;

	private double promotionPrice;

	private String desc;

	private String content;

	private int cateId;

	private Date lastUpdate;

	private String cateName;

	private String icon;

	private int teacherId;

	private String teacherName;

	public CourseDto() {

	}

	// CourseDto(c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount,
	// c.price, c.discount,"
	// + " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
	// + " ca.icon, u.id, u.fullname) "

	public CourseDto(
			int id, 
			String title, 
			String image, 
			int lectureCount, 
			int hourCount, 
			int viewCount, 
			double price,
			int discount, 
			double promotionPrice, 
			String desc, 
			String content, 
			int cateId, 
			Date lastUpdate,
			String cateName, 
			String icon, 
			int teacherId, 
			String teacherName) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.lectureCount = lectureCount;
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
		this.icon = icon;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
	}
	
	public CourseDto(
			int id, 
			String title, 
			String image, 
			int lectureCount, 
			int hourCount, 
			int viewCount, 
			double price,
			int discount, 
			double promotionPrice, 
			String desc, 
			String content, 
			Date lastUpdate) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.lectureCount = lectureCount;
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
		this.icon = icon;
		this.teacherId = teacherId;
		this.teacherName = teacherName;
	}
	
	public CourseDto(
			int id, 
			String title, 
			String image, 
			int lectureCount, 
			int hourCount, 
			int viewCount, 
			double price,
			int discount, 
			double promotionPrice, 
			String desc, 
			String content, 
			int cateId, 
			Date lastUpdate,
			String cateName, 
			String icon, 
			int teacherId) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.lectureCount = lectureCount;
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
		this.icon = icon;
		this.teacherId = teacherId;
	}

	public CourseDto(
			int id, 
			String title, 
			String image, 
			int lectureCount, 
			int hourCount, 
			int viewCount, 
			double price,
			int discount, 
			double promotionPrice, 
			String desc, 
			String content, 
			int cateId, 
			Date lastUpdate,
			String cateName, 
			String icon) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.lectureCount = lectureCount;
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
		this.icon = icon;
	}

	// int, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	// int, java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	// int, java.util.Date, java.lang.String, java.lang.Strings
	public CourseDto(
			int id, 
			String title, 
			String image, 
			int hourCount, 
			int viewCount, 
			double price, 
			int discount,
			double promotionPrice, 
			String desc, 
			String content, 
			int cateId, 
			Date lastUpdate, 
			String cateName,
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
		this.icon = icon;
	}

	public CourseDto(
			int id, 
			String title, 
			String image, 
			int hourCount, 
			int viewCount, 
			double price, 
			int discount,
			double promotionPrice, 
			String desc, 
			String content, 
			int cateId, 
			Date lastUpdate) {
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

	public int getLectureCount() {
		return lectureCount;
	}

	public void setLectureCount(int lectureCount) {
		this.lectureCount = lectureCount;
	}

	public int getHourCount() {
		return hourCount;
	}

	public void setHourCount(int hourCount) {
		this.hourCount = hourCount;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public double getPromotionPrice() {
		return promotionPrice;
	}

	public void setPromotionPrice(double promotionPrice) {
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public int getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}

}
