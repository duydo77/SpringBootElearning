package com.myclass.controller.teacher;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.ImageDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.UploadVideoDto;
import com.myclass.dto.UploadVideoFileDto;
import com.myclass.dto.VideoDto;
import com.myclass.dto.VideoFileDto;
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
			
			int videoId = videoService.addAndReturnId(dto);
			return new ResponseEntity<Object>(videoId, HttpStatus.OK);
		
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
	
	@PostMapping(value = "/file/{id}")
	public Object post(@PathVariable("id") int id, @RequestBody MultipartFile file) {
		try {
			System.out.println("controller " + file);
			videoService.update(id, file);
			return new ResponseEntity<Object>(HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("video/{videoName}")
    public Object getImage(@PathVariable String videoName) {
		try {
			VideoFileDto dto = videoService.getVideoFile(videoName);
    		return ResponseEntity.ok()
    				.contentLength(dto.getLength())
    				.contentType(MediaType.parseMediaType("video/mp4"))
    				.body(dto.getByteArrayResource());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }
}

