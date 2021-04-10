package com.myclass.service;

import java.util.List;

import com.myclass.dto.CourseDtoM;

public interface CourseServiceM {

	public List<CourseDtoM> findAll();

	public CourseDtoM findById(int id);

	public void update(int id, CourseDtoM dto);

	public void add(CourseDtoM dto);

	public void delete(int id);
}
