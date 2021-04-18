
package com.myclass.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "user_courses")
public class UserCourse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserCourseKey id;

	@ManyToOne
//	@MapsId("userId")
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
	private User user;

	@ManyToOne
//	@MapsId("courseId")
	@JoinColumn(name = "course_id", insertable = false, updatable = false)
	private Course course;

	private int roleId;

	public UserCourse() {

	}

	public UserCourse(UserCourseKey id, int role_id) {
		this.id = id;
		this.role_id = role_id;
	}
	
	public UserCourse(UserCourseKey id, User user, Course course, int role_id) {
		super();
		this.id = id;
		this.roleId = roleId;
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

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

}
