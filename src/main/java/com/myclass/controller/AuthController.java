package com.myclass.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;
import com.myclass.dto.UpdateProfileReponseDto;
import com.myclass.dto.UserDto;
import com.myclass.service.AuthService;
import com.myclass.service.UserService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private AuthService authServive;
	
	@PostMapping("login")
	public Object post(@RequestBody LoginDto loginDto) {
		if (userService.getRoleByEmail(loginDto.getEmail()).equals("ROLE_STUDENT")) {
			try {
				String token = authServive.login(loginDto);
				return new ResponseEntity<Object>(token, HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("register")
	public Object post(@RequestBody UserDto userDto) {
		userDto.setAddress("");
		userDto.setPhone("");
		try {
			UpdateProfileReponseDto message  = userService.add(userDto);
			String token = "";
			if (message.getMessage().equals("0")) {
				token = authServive.login(new LoginDto(userDto.getEmail(), userDto.getPassword()));
			}
			message.setToken(token);
			return new ResponseEntity<Object>(message, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);	
	}

}
