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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserCourseKey other = (UserCourseKey) obj;
		if (courseId != other.courseId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
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
