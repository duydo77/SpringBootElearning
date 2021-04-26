package com.myclass.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;
import com.myclass.dto.UserDto;
import com.myclass.service.AuthService;
import com.myclass.service.UserService;

@RestController
@Scope("prototype")
@RequestMapping("api/teacher/")
@CrossOrigin("*")
public class TeacherLoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authService;

	@PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object post(@RequestBody LoginDto loginDto) {
		if (userService.getRoleByEmail(loginDto.getEmail()).equals("ROLE_TEACHER")) {
			try {
				String token = authService.login(loginDto);

				return new ResponseEntity<Object>(token, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("resgister")
	public Object post(@RequestBody UserDto userDto) {
		try {
			userService.add(userDto);
			return new ResponseEntity<Object>(HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
