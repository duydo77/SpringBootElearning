package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;
import com.myclass.service.RoleService;

@Service
@Scope("prototype")
public class RoleServiceImpl implements RoleService {
	private RoleRepository roleRepository;

	RoleServiceImpl(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public List<RoleDto> findAll() {
		List<Role> entities = roleRepository.findAll();
		List<RoleDto> dtos = new ArrayList<RoleDto>();

		for (Role entity : entities) {
			dtos.add(new RoleDto(entity.getId(), entity.getName(), entity.getDesc()));
		}

		return dtos;
	}

	@Override
	public RoleDto findById(int id) {
		Role entity = roleRepository.getOne(id);
		return new RoleDto(entity.getId(), entity.getName(), entity.getDesc());
	}

	@Override
	public void update(int id, RoleDto dto) {
		if (roleRepository.existsById(id)) {
			Role entity = roleRepository.getOne(dto.getId());
			if (entity == null)
				return;
			entity.setName(dto.getName());
			entity.setDesc(dto.getDesc());
			roleRepository.save(entity);
		}
	}

	@Override
	public void add(RoleDto dto) {
		Role entity = new Role(dto.getName(), dto.getDesc());
		roleRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		roleRepository.deleteById(id);
	}
}
