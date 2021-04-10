package com.myclass.service;

import java.util.List;

import com.myclass.dto.RoleDto;

public interface RoleService {

	public List<RoleDto> findAll();

	public RoleDto findById(int id);

	public void update(int id, RoleDto dto);

	public void add(RoleDto dto);

	public void delete(int id);
}
