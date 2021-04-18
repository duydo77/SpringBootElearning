package com.myclass.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.myclass.dto.UserCourseDto;
import com.myclass.service.UserCourseService;

@Service
@Transactional(rollbackOn = Exception.class )
public class UserCourseServiceImpl implements UserCourseService{

	@Override
	public void add(UserCourseDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, UserCourseDto dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

	
}
