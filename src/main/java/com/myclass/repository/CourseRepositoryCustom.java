package com.myclass.repository;

import java.util.List;
import java.util.Map;

import com.myclass.dto.CourseDto;
import com.myclass.dto.SearchDto;

public interface CourseRepositoryCustom {
	
	List<CourseDto> searchCourse(SearchDto dto);
	
}
