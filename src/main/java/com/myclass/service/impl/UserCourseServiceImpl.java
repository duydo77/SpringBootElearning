package com.myclass.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myclass.dto.UserCourseDto;
import com.myclass.dto.UserDetailsDto;
import com.myclass.entity.UserCourse;
import com.myclass.entity.UserCourseKey;
import com.myclass.repository.UserCourseRepository;
import com.myclass.service.UserCourseService;

@Service
@Transactional(rollbackOn = Exception.class )
public class UserCourseServiceImpl implements UserCourseService{

	@Autowired
	private UserCourseRepository userCourseRepository;
	
	@Override
	public void add(int courseId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = ((UserDetailsDto) principal).getId();
		int roleId = ((UserDetailsDto) principal).getRoleId();
		UserCourseKey userCoursePK = new UserCourseKey(userId, courseId);
		
		UserCourse userCourse = new UserCourse(userCoursePK, roleId);
		userCourseRepository.save(userCourse);
	}

	@Override
	public void update(int id, UserCourseDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean isBoughtCheck(int courseId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = ((UserDetailsDto) principal).getId();
		
		if(userCourseRepository.findByCourseIdAndUserId(courseId, userId) ==  null) {
			return false;
		} else {
			return true;
		}
	}
	
}
