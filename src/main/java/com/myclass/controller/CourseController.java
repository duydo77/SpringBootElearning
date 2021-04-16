package com.myclass.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CourseDetailsDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.VideoDto;
import com.myclass.service.CourseService;
import com.myclass.service.TargetService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping("api/course")
public class CourseController {

	private CourseService courseService;
	private TargetService targetService;
	private VideoService videoService;
	
	CourseController(CourseService courseService, TargetService targetService, VideoService videoService){
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
	
	@GetMapping("{id}")
	public Object get(@PathVariable int id) {
		try {
			CourseDto courseDto = courseService.findById(id);
			List<TargetDto> targetDtos = targetService.findByCourseId(courseDto.getId());
			List<VideoDto> videoDtos = videoService.findByCourseId(courseDto.getId());
			CourseDetailsDto dto = new CourseDetailsDto(courseDto, videoDtos, targetDtos); 
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
					
		}catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
