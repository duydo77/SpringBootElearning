package com.myclass.dto;

import java.util.List;

import com.myclass.entity.Category;

public class CourseDetailsDto {

	private CourseDto course;
	private List<VideoDto> videos;
	private List<TargetDto> targets;
	private UserCourseDto userCourse;
	private UserDto user;
	private Category category;
	private CategoryDto categoryDto;
	
	public CourseDetailsDto() {}
	
	public CourseDetailsDto(CourseDto course, List<VideoDto> videos, List<TargetDto> targets) {
		this.course = course;
		this.videos = videos;
		this.targets = targets;
	}

	public CourseDetailsDto(CourseDto course, List<VideoDto> videos, List<TargetDto> targets, UserDto user, CategoryDto category) {
		this.course = course;
		this.videos = videos;
		this.targets = targets;
		this.user = user;
		this.categoryDto = category;
	}
	
	public CourseDetailsDto(CourseDto course, List<VideoDto> videos, List<TargetDto> targets, UserCourseDto userCourse,
			UserDto user, Category category) {
		this.course = course;
		this.videos = videos;
		this.targets = targets;
		this.userCourse = userCourse;
		this.user = user;
		this.category = category;
	}

	public UserCourseDto getUserCourse() {
		return userCourse;
	}

	public void setUserCourse(UserCourseDto userCourse) {
		this.userCourse = userCourse;
	}

	public UserDto getUser() {
		return user;
	}

	public void setUser(UserDto user) {
		this.user = user;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}
//
//	public List<VideoDto> getVideo() {
//		return videos;
//	}
//
//	public void setVideo(List<VideoDto> videos) {
//		this.videos = videos;
//	}
//
//	public List<TargetDto> getTarget() {
//		return targets;
//	}
//
//	public void setTarget(List<TargetDto> targets) {
//		this.targets = targets;
//	}

	public List<VideoDto> getVideos() {
		return videos;
	}

	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
	}

	public List<TargetDto> getTargets() {
		return targets;
	}

	public void setTargets(List<TargetDto> targets) {
		this.targets = targets;
	}

	public CategoryDto getCategoryDto() {
		return categoryDto;
	}

	public void setCategoryDto(CategoryDto categoryDto) {
		this.categoryDto = categoryDto;
	}
	
	
}
