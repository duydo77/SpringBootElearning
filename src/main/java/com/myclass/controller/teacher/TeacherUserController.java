package com.myclass.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.LoginDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;
import com.myclass.service.AuthService;
import com.myclass.service.UserService;

@RestController
@Scope("prototype")
@RequestMapping("api/teacher/profile")
public class TeacherUserController {
	@Autowired
	private UserService userService ;
	
	@GetMapping("")
	public Object get() {
		try {
			UserDto userDto = userService.getProfile2();
			return new ResponseEntity<Object>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("")
	public Object put(@RequestBody UserDto dto) {
		try {
			userService.update(dto);
			return new ResponseEntity<Object>( HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("password")
	public Object put(@RequestBody PasswordDto passwordDto) {
		try {
			String message = userService.changePassword2(passwordDto);
			if (message != null) {
				return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
