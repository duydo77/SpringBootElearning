package com.myclass.controller.teacher;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping(value = "api/teacher/course")
public class TeacherCourseController {
	private CourseService courseService;
	private TargetService targetService;
	private VideoService videoService;
//	private UserCourseService userCourseService;
	
	TeacherCourseController(CourseService courseService, TargetService targetService, VideoService videoService){
		this.courseService = courseService;
		this.targetService = targetService;
		this.videoService = videoService;
//		this.userCourseService = userCourseService;
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
	
	@GetMapping("/ofteacher")
	public Object getOfTeacher() {
		try {
			List<CourseDto> dtos = courseService.findAllOfTeacher();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping
	public Object post(@RequestBody CourseDto body) {
		try {
			courseService.add(body);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public Object put(@RequestBody CourseDto body) {
		try {
			courseService.update(body);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable("id") int id) {
		try {
			courseService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	//test
	@GetMapping(value = "/{idc}")
	public Object get(@PathVariable("idc") int idc) {
		try {
			Course entity = courseService.test(idc);
			return new ResponseEntity<Object>(entity, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping(value = "test/{idc}")
	public Object get1(@PathVariable("idc") int idc) {
		try {
			Course entity = courseService.test(idc);
			return new ResponseEntity<Object>(entity, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
