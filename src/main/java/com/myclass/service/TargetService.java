package com.myclass.service;

import java.util.List;

import com.myclass.dto.TargetDto;

public interface TargetService {
	
	List<TargetDto> findByCourseId(int courseId);

}
