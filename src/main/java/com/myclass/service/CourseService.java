package com.myclass.service;

import java.util.List;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;

public interface CourseService {

	public List<CourseDto> findAll();

	public CourseDto findById(int id);

	public void update(CourseDto dto);
	
	public void update(int id, CourseDto dto);

	public void add(CourseDto dto);
	
	public void delete(int id);
	
	public CourseDto findDetailsCourseById(int id);
	
	public List<CourseDto> findAllOfTeacher();
	
	Course test(int id);
	
	public List<CourseDto> findPromotion();
	public List<CourseDto> findPopular();
}
