package com.myclass.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.myclass.dto.CourseDto;
import com.myclass.dto.UserDetailsDto;
import com.myclass.entity.Course;
import com.myclass.repository.CourseRepository;
import com.myclass.service.CourseService;

@Service
@Scope("prototype")
@Transactional(rollbackOn = Exception.class)
public class CourseServiceImpl implements CourseService {

	private CourseRepository courseRepository;

	CourseServiceImpl(CourseRepository courseRepository) {
		this.courseRepository = courseRepository;
	}

	@Override
	public List<CourseDto> findAll() {
		return courseRepository.findAllWithCate();
	}

	@Override
	public CourseDto findById(int id) {
		Course entity = courseRepository.getOne(id);
		return new CourseDto(entity.getId(), entity.getTitle(), entity.getImage(), entity.getHourCount(),
				entity.getViewCount(), entity.getPrice(), entity.getDiscount(), entity.getPromotionPrice(),
				entity.getDesc(), entity.getContent(), entity.getCateId(), entity.getLastUpdate());
	}

	@Override
	public void update(int id, CourseDto dto) {
		if (courseRepository.existsById(id)) {
			Course entity = courseRepository.getOne(dto.getId());
			if (entity == null)
				return;
			entity.setId(dto.getId());
			entity.setTitle(dto.getTitle());
			entity.setImage(dto.getImage());
			entity.setHourCount(dto.getHourCount());
			entity.setViewCount(dto.getViewCount());
			entity.setPrice(dto.getPrice());
			entity.setDiscount(dto.getDiscount());
			entity.setPromotionPrice(dto.getPromotionPrice());
			entity.setDesc(dto.getDesc());
			entity.setContent(dto.getContent());
			entity.setCateId(dto.getCateId());
			entity.setLastUpdate(dto.getLastUpdate());
			courseRepository.save(entity);
		}

	}

	@Override
	public void add(CourseDto dto) {

		Course entity = new Course(dto.getId(), 
				dto.getTitle(), 
				dto.getImage(), 
				dto.getLectureCount(), 
				dto.getHourCount(), 
				dto.getViewCount(), 
				dto.getPrice(), dto.getDiscount(), 
				dto.getPromotionPrice(), dto.getDesc(), 
				dto.getContent(), dto.getCateId(), 
				dto.getLastUpdate());
		
		courseRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		courseRepository.deleteById(id);
	}

	@Override
	public void test() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = ((UserDetailsDto)principal).getUsername();
		int id = ((UserDetailsDto)principal).getId();
		int roleId = ((UserDetailsDto)principal).getRoleId();
		System.out.println(email);
		System.out.println(id);
		System.out.println(roleId);
	}
	
}
