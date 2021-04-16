package com.myclass.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserCourseKey implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "user_id")
	int userId;
	
	@Column(name = "course_id")
	int courseId;

	public UserCourseKey() {
	}
	
	public UserCourseKey(int userId, int courseId) {
		this.userId = userId;
		this.courseId = courseId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	
}
