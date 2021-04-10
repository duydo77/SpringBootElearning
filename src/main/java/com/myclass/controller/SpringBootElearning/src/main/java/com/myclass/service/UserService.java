package com.myclass.service;

import java.util.List;

import com.myclass.dto.UserDto;

public interface UserService {

	public List<UserDto> findAll();

	public UserDto findById(int id);

	public void update(int id, UserDto dto);

	public void add(UserDto dto);

	public void delete(int id);
}
