package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.myclass.dto.CategoryDto;
import com.myclass.entity.Category;
import com.myclass.repository.CategoryRepository;
import com.myclass.service.CategoryService;

@Service
@Scope("prototype")
public class CategoryServiceImpl implements CategoryService {

	private CategoryRepository categoryRepository;

	CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	@Override
	public List<CategoryDto> findAll() {
		List<Category> entities = categoryRepository.findAll();
		List<CategoryDto> dtos = new ArrayList<CategoryDto>();

		for (Category entity : entities) {
			dtos.add(new CategoryDto(entity.getId(), entity.getName(), entity.getIcon()));
		}

		return dtos;
	}

	@Override
	public CategoryDto findById(int id) {
		Category entity = categoryRepository.getOne(id);
		return new CategoryDto(entity.getId(), entity.getName(), entity.getIcon());
	}

	@Override
	public void update(int id, CategoryDto dto) {
		if (categoryRepository.existsById(id)) {
			Category entity = categoryRepository.getOne(dto.getId());
			if (entity == null)
				return;
			entity.setIcon(dto.getIcon());
			entity.setName(dto.getName());
			categoryRepository.save(entity);
		}

	}

	@Override
	public void add(CategoryDto dto) {
		Category entity = new Category(dto.getName(), dto.getIcon());
		categoryRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		categoryRepository.deleteById(id);
	}

}
