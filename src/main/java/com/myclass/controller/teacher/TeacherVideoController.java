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

import com.myclass.dto.TargetDto;
import com.myclass.dto.UploadVideoDto;
import com.myclass.dto.VideoDto;
import com.myclass.service.TargetService;
import com.myclass.service.VideoService;

@RestController
@RequestMapping(value = "api/teacher/video")
public class TeacherVideoController {

	private VideoService videoService;
	
	public TeacherVideoController(VideoService videoService) {
		this.videoService = videoService;
	}
	
	@GetMapping("/{courseid")
	public Object getAllByCourseId(@PathVariable("courseid") int courseId) {
		try {
			List<VideoDto> dtos = videoService.findByCourseId(courseId);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}")
	public Object findById(@PathVariable("id") int id) {
		try {
			VideoDto dto = videoService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping
	public Object post(@RequestBody VideoDto dto) {
		try {
			
			videoService.add(dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("/base64")
	public Object post(@RequestBody UploadVideoDto dto) {
		try {
			System.out.println("controller" + dto.getTitle());
			System.out.println("controller" + dto.getVideoAsBase64());
			videoService.add(dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public Object put(@RequestBody VideoDto dto) {
		try {
			
			videoService.update(dto.getId(), dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable("id") int id) {
		try {
			
			videoService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}

