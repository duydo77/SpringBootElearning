package com.myclass.controller.admin;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
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

import com.myclass.dto.UserDto;
import com.myclass.service.UserService;
import com.myclass.utils.UpdateErrorCode;

@RestController
@Scope("prototype")
@RequestMapping("api/admin/user")
public class AdminUserController {

	@Autowired
	private UserService userService;
	
	@GetMapping("")
	public Object get() {
		// lấy full danh sách người dùng có cả tên quyền
		try {
			List<UserDto> dtos = userService.findAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PostMapping("")
	public Object post(@Valid @RequestBody UserDto body) {
		// them moi nguoi dung
		try {
			String message = userService.add(body);
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/{id}")
	public Object get(@PathVariable("id") int id) {
		// tim nguoi dung voi id, tra ve userdto
		try {
			UserDto dto = userService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public Object put(@PathVariable("id") int id, @RequestBody UserDto dto) {
		// Cap nhat nguoi dung voi id (chi co admin moi co quyen cap nhat user qua id)
		try {
			String message = userService.update(id, dto);
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable("id") int id) {
		// xoa nguoi dung
		try {
			userService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
