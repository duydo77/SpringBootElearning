package com.myclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;
import com.myclass.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	@Autowired
	private UserService userSerice;
	
	@GetMapping("profile")
	public Object get() {
		try {
			UserDto userDto = userSerice.getProfile();
			return new ResponseEntity<Object>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("password")
	public Object post(@RequestBody PasswordDto passwordDto) {
		try {
			String message = userSerice.changePassword(passwordDto);
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
