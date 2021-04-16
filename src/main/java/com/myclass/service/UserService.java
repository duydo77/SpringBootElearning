package com.myclass.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.ImageDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UpdateProfileReponseDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.UserUpdateDto;

public interface UserService {

	public List<UserDto> findAll();

	public UserDto findById(int id);

	public UpdateProfileReponseDto update(UserUpdateDto dto);
	
	public String update(int id, UserDto dto);

	public void add(UserDto dto);

	public void delete(int id);

	UserDto getProfile();
	
	String changePassword(PasswordDto passwordDto);
	
	String updateAvatar(MultipartFile file) throws IOException;
	
	ImageDto getAvatar(String imageName) throws IOException;
	
}
