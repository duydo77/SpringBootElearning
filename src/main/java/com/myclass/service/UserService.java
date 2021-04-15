package com.myclass.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.ImageDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UserDto;

public interface UserService {

	public List<UserDto> findAll();

	public UserDto findById(int id);

	public String update(UserDto dto);
	
	public String update(int id, UserDto dto);

	public void add(UserDto dto);

	public void delete(int id);

	UserDto getProfile();
	
	String changePassword(PasswordDto passwordDto);
	
	String updateAvatar(MultipartFile file) throws IOException;
	
	ImageDto getAvatar(String imageName) throws IOException;
}
