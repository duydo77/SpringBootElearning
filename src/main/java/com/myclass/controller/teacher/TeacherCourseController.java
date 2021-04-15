package com.myclass.controller.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CourseDto;
import com.myclass.dto.RoleDto;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping(value = "api/teacher/course", 
	consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.APPLICATION_XML_VALUE},
	produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@PostMapping
	public Object post( CourseDto body) {
		try {
			courseService.add(body);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	// this is test
	@GetMapping("/test")
	public Object getTest() {
		try {
			courseService.test();
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
