package com.myclass.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.naming.directory.SearchControls;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CourseDetailsDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.SearchDto;
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
	
	@GetMapping("promotion")
	public Object getPromotion() {
		try {
			List<CourseDto> dtos = courseService.findPromotion();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("popular")
	public Object getNormal() {
		try {
			List<CourseDto> dtos = courseService.findPopular();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		}catch(Exception e) {
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
	
	@GetMapping("/mycourse")
	public Object mycourse() {
		try {
			List<CourseDto> dtos = courseService.findAllOfUser();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/search")
	public Object search(@RequestParam String key) {
		try {
			List<CourseDto> dtos = courseService.search(key);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/cate/{cateId}")
	public Object searchCategory(@PathVariable("cateId") int cateId) {
		try {
			List<CourseDto> dtos = courseService.findByCateId(cateId);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/filter")
	public Object test(@RequestBody SearchDto dto) {
		try {
			List<CourseDto> dtos = courseService.searchFilter(dto);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST); 
	}
}
