package com.myclass.service;

import java.util.List;

import com.myclass.dto.RoleDto;
import com.myclass.dto.TargetDto;

public interface TargetService {
	
	List<TargetDto> findByCourseId(int courseId);
	
	public List<TargetDto> findAll();
	
	public TargetDto findById(int id);

	public void update(int id, TargetDto dto);

	public void add(TargetDto dto);

	public void delete(int id);

}
