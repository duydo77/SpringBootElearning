package com.myclass.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.ImageDto;
import com.myclass.dto.PasswordDto;
import com.myclass.dto.UpdateProfileReponseDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.UserUpdateDto;
import com.myclass.entity.User;

public interface UserService {

	public List<UserDto> findAll();

	public UserDto findById(int id);

	public UpdateProfileReponseDto update(UserUpdateDto dto);
	
	public String update(int id, UserDto dto);

	public UpdateProfileReponseDto add(UserDto dto);

	public void addTeacher(UserDto dto);
	
	public void delete(int id);

	UserDto getProfile();
	
	UserDto getProfile2();
	
	UpdateProfileReponseDto changePassword(PasswordDto passwordDto);
	
	String updateAvatar(MultipartFile file) throws IOException;
	
	ImageDto getAvatar(String imageName) throws IOException;
	
	String changePassword2(PasswordDto passwordDto);
	
	public void update(UserDto dto);
	
	public String getRoleByEmail(String email);
	
	List<User> findAllByCourseId(int courseId);
}
