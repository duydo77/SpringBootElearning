package com.myclass.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myclass.dto.CourseDto;
import com.myclass.dto.UserDetailsDto;
import com.myclass.entity.Course;
import com.myclass.entity.User;
import com.myclass.entity.UserCourse;
import com.myclass.entity.UserCourseKey;
import com.myclass.entity.User;
import com.myclass.repository.CourseRepository;
import com.myclass.repository.UserCourseRepository;
import com.myclass.service.CourseService;

@Service
@Scope("prototype")
@Transactional(rollbackOn = Exception.class)
public class CourseServiceImpl implements CourseService {

	private CourseRepository courseRepository;
	private UserCourseRepository userCourseRepository;

	CourseServiceImpl(CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
		this.courseRepository = courseRepository;
		this.userCourseRepository = userCourseRepository;
	}

	@Override
	public List<CourseDto> findAll() {
		List<CourseDto> courseDtos = courseRepository.findAllWithCate();
//		for (CourseDto courseDto: courseDtos) {
//			System.out.println(courseDto.getId());
//			User teacher = courseRepository.findTeacher(courseDto.getId());
//			courseDto.setTeacherId(teacher.getId());
//			courseDto.setTeacherName(teacher.getFullname());
//		}
		return courseDtos;
	}

	@Override
	public CourseDto findById(int id) {
//		Course entity = courseRepository.getOne(id);
//		return new CourseDto(entity.getId(), entity.getTitle(), entity.getImage(), entity.getHourCount(),
//				entity.getViewCount(), entity.getPrice(), entity.getDiscount(), entity.getPromotionPrice(),
//				entity.getDesc(), entity.getContent(), entity.getCateId(), entity.getLastUpdate(), teacher.getId(), teacher.getFullname());
		
		CourseDto courseDto = courseRepository.findById(id);
		User teacher = courseRepository.findTeacher(courseDto.getId());
		courseDto.setTeacherId(teacher.getId());
		courseDto.setTeacherName(teacher.getFullname());
		return courseDto;
	}

	@Override
	public void update(int id, CourseDto dto) {

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
	
	@Override
	public void update(CourseDto dto) {

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

	@Override
	public void add(CourseDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = ((UserDetailsDto)principal).getId();
		int roleId = ((UserDetailsDto)principal).getRoleId();
		
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
		
		int id = courseRepository.saveAndFlush(entity).getId();
		System.out.println("Before nn table");
		UserCourseKey userCourseKey = new UserCourseKey(userId, id);
		UserCourse userCourse = new UserCourse(userCourseKey, roleId);
		userCourseRepository.save(userCourse);
		System.out.println("After nn table");
	}

	@Override
	public void delete(int id) {
		courseRepository.deleteById(id);
		userCourseRepository.deleteById(id);
	}
	
	@Override
	public CourseDto findDetailsCourseById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CourseDto> findAllOfTeacher() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = ((UserDetailsDto)principal).getId();
		return courseRepository.findAllOfTeacher(id);
	}
	
	@Override
	public Course test(int courseId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = ((UserDetailsDto)principal).getId();
		return courseRepository.test(id, courseId);
	}
	
}
