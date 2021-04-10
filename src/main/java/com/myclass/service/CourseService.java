package com.myclass.service;

import java.util.List;

import com.myclass.dto.CourseDto;

public interface CourseService {

	public List<CourseDto> findAll();

	public CourseDto findById(int id);

	public void update(int id, CourseDto dto);

	public void add(CourseDto dto);

	public void delete(int id);
}
