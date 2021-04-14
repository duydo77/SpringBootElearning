package com.myclass.controller.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CourseDto;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping("api/teacher/course")
public class TeacherCourseController {
	private CourseService courseService;
	private TargetService targetService;
	private VideoService videoService;
	
	TeacherCourseController(CourseService courseService, TargetService targetService, VideoService videoService){
		this.courseService = courseService;
		this.targetService = targetService;
		this.videoService = videoService;
	}
	
	@GetMapping
	public Object get() {
		try {
			List<CourseDto> dtos = courseService.findAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
//	@PostMapping
//	public Object post() {
//		try {
//			List<CourseDto> dtos = courseService.findAll();
//			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
//	}
}
