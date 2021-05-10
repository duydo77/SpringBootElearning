package com.myclass.dto;

import com.myclass.entity.Course;
import com.myclass.entity.User;
import com.myclass.entity.UserCourseKey;

public class UserCourseDto {

	private UserCourseKey id;
	private User user;
	private Course course;
	private int role_id;

	public UserCourseDto() {
	}

	public UserCourseDto(UserCourseKey id, User user, Course course, int role_id) {
		super();
		this.id = id;
		this.role_id = role_id;
	}

	public UserCourseKey getId() {
		return id;
	}

	public void setId(UserCourseKey id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
}
