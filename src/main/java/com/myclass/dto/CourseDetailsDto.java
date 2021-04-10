package com.myclass.dto;

import java.util.List;

public class CourseDetailsDto {

	private CourseDto course;
	private List<VideoDto> video;
	private List<TargetDto> target;

	public CourseDetailsDto(CourseDto course, List<VideoDto> video, List<TargetDto> target) {
		this.course = course;
		this.video = video;
		this.target = target;
	}

	public CourseDto getCourse() {
		return course;
	}

	public void setCourse(CourseDto course) {
		this.course = course;
	}

	public List<VideoDto> getVideo() {
		return video;
	}

	public void setVideo(List<VideoDto> video) {
		this.video = video;
	}

	public List<TargetDto> getTarget() {
		return target;
	}

	public void setTarget(List<TargetDto> target) {
		this.target = target;
	}

}
