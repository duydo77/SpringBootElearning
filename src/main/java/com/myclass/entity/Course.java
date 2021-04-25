package com.myclass.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "courses")
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "image")
	private String image;
	
	@Column(name = "lecture_count", length = 11)
	private int lectureCount;
	
	@Column(name = "hour_count", length = 11)
	private int hourCount;
	
	@Column(name = "view_count", length = 11)
	private int viewCount;
	
	@Column(name = "price", precision = 10, scale = 0)
	private double price;
	
	@Column(name = "discount", length = 11)
	private int discount;
	
	@Column(name = "promotion_price", precision = 10, scale = 0)
	private double promotionPrice;
	
	@Column(name = "description", length = 100)
	private String desc;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "category_id", length = 11)
	private int cateId;
	
	@Column(name = "last_update")
	private Date lastUpdate;
	
	@ManyToOne
	@JoinColumn(name = "category_id", insertable = false, updatable = false)
	private Category category;

	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<Target> targets;
	
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<Video> videos;
	
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<UserCourse> userCourses;
	
	public Course() {
		
	}
	
	public Course(int id, String title, String image, int lectureCount, int hourCount, int viewCount, double price,
			int discount, double promotionPrice, String desc, String content, int cateId, Date lastUpdate) {
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Target> getTargets() {
		return targets;
	}

	public void setTargets(List<Target> targets) {
		this.targets = targets;
	}

	public List<Video> getVideos() {
		return videos;
	}

	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}

	public List<UserCourse> getUserCourses() {
		return userCourses;
	}

	public void setUserCourses(List<UserCourse> userCourses) {
		this.userCourses = userCourses;
	}
	
}
