package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.TargetDto;
import com.myclass.entity.Target;
import com.myclass.repository.TargetRepository;
import com.myclass.service.TargetService;

@Service
@Transactional(rollbackOn = Exception.class)
public class TargetServiceImpl implements TargetService {

	@Autowired
	TargetRepository targetRepository;
	
	@Override
	public List<TargetDto> findByCourseId(int courseId) {
		List<Target> entities = targetRepository.findByCourseId(courseId);
		List<TargetDto> dtos = new ArrayList<TargetDto>();
		for (Target entity: entities) {
			TargetDto dto = new TargetDto();
			dto.setId(entity.getId());
			dto.setTitle(entity.getTitle());
			dto.setCourseId(entity.getCourseId());
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void add(TargetDto dto) {
		Target entity = new Target(dto.getTitle(), dto.getCourseId());
		targetRepository.save(entity);
	}
	
	@Override
	public void update(TargetDto dto) {
		Target entity = new Target(dto.getId(), dto.getTitle(), dto.getCourseId());
		targetRepository.save(entity);
	}
	
	@Override
	public TargetDto findById(int id) {
		Target entity = targetRepository.getOne(id);
		return new TargetDto(entity.getId(), entity.getTitle(), entity.getCourseId());
	}
	
	@Override
	public void delete(int id) {
		Target entity = targetRepository.getOne(id);
		targetRepository.delete(entity);
	}
}
