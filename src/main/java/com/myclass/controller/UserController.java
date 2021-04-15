package com.myclass.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.ImageDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;
import com.myclass.service.UserService;

@RestController
@RequestMapping("api/user")
public class UserController {

	private UserService userService;

	UserController(UserService userSerice) {
		this.userService = userSerice;
	}

	@GetMapping("profile")
	public Object get() {
		try {
			UserDto userDto = userService.getProfile();
			return new ResponseEntity<Object>(userDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST); // email, fullname, avatar, phone, address
	}

	@PutMapping("update")
	public Object put(@RequestBody UserDto userDto) {
		// cập nhật chỉ người đang đăng nhập
		try {
			String message = userService.update(userDto);
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}

	@PostMapping("updateAvatar")
	public Object uploadFile(@RequestBody MultipartFile file) {
		// Cập nhật avatar
		try {
			String message = userService.updateAvatar(file);
			return new ResponseEntity<Object>(message, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("getAvatar/{imageName}")
    public Object getImage(@PathVariable String imageName) {
		// Trả về ảnh avatar
		try {
			ImageDto imageDto = userService.getAvatar(imageName);
    		return ResponseEntity.ok()
    				.contentLength(imageDto.getLength())
    				.contentType(MediaType.parseMediaType("image/png"))
    				.body(imageDto.getByteArrayResource());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
    }

	@PutMapping("password")
	public Object put(@RequestBody PasswordDto passwordDto) {
		// Cập nhật password
		try {
			String message = userService.changePassword(passwordDto);
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
