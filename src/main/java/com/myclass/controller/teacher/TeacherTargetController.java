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
import com.myclass.service.TargetService;

@RestController
@RequestMapping(value = "api/teacher/target")
public class TeacherTargetController {

	private TargetService targerService;
	
	public TeacherTargetController(TargetService targerService) {
		this.targerService = targerService;
	}
	
	@GetMapping("/{courseid")
	public Object getAllByCourseId(@PathVariable("courseid") int courseId) {
		try {
			List<TargetDto> dtos = targerService.findByCourseId(courseId);
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}")
	public Object findById(@PathVariable("id") int id) {
		try {
			TargetDto dto = targerService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping
	public Object post(@RequestBody TargetDto dto) {
		try {
			
			targerService.add(dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping
	public Object put(@RequestBody TargetDto dto) {
		try {
			
			targerService.update(dto.getId(), dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable("id") int id) {
		try {
			
			targerService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
