package com.myclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.service.UserCourseService;

@RestController
@RequestMapping("api/usercourse")
public class UserCourseController {

	@Autowired
	private UserCourseService userCourseService;
	
	@PostMapping("{courseId}")
	public Object post(@PathVariable int courseId) {
		try {
			userCourseService.add(courseId);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
