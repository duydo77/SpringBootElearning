package com.myclass.service;

import com.myclass.dto.UserCourseDto;

public interface UserCourseService {

	void add(int courseId);
	void update(int id, UserCourseDto dto);
	void delete(int id);
	boolean isBoughtCheck(int courseId);
}
