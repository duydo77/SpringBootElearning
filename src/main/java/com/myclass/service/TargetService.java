package com.myclass.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myclass.dto.TargetDto;

@Service
public interface TargetService {
	
	List<TargetDto> findByCourseId(int courseId);

	void add(TargetDto dto);
	
	void update(TargetDto dto);
	
	TargetDto findById(int id);
	
	void delete(int id);
}
