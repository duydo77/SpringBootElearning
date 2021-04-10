package com.myclass.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myclass.dto.LoginDto;
import com.myclass.service.AuthService;

@RestController
@Scope("prototype")
@RequestMapping("api/admin/login")
@CrossOrigin("*")
public class AdminLoginController {

	@Autowired
	private AuthService authService;
	
	@PostMapping("")
	public Object post(@RequestBody LoginDto loginDto) {
		try {
			String token = authService.login(loginDto);

			return new ResponseEntity<Object>(token, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
