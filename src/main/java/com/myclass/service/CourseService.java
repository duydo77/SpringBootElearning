package com.myclass.service;

import java.util.List;

import com.myclass.dto.CourseDetailsDto;
import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;

public interface CourseService {

	public List<CourseDto> findAll();

	public CourseDto findById(int id);

	public void update(CourseDto dto);
	
	public void update(int id, CourseDto dto);

	public void add(CourseDto dto);
	
	public void delete(int id);
	
//	public CourseDto findDetailById(int id);
	
	public CourseDetailsDto findDetailById(int id);
	
	public List<CourseDto> findAllOfTeacher();
	
	Course test(int id);
	
	public List<CourseDto> findPromotion();
	public List<CourseDto> findPopular();

	List<CourseDto> findAllOfUser();

	public List<CourseDto> findByCateId(int cateId);
	
	public List<CourseDto> search(String key);
}
