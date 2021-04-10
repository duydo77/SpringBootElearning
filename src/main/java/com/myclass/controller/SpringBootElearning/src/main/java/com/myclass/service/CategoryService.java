package com.myclass.service;

import java.util.List;

import com.myclass.dto.CategoryDto;

public interface CategoryService {

	public List<CategoryDto> findAll();

	public CategoryDto findById(int id);

	public void update(int id, CategoryDto dto);

	public void add(CategoryDto dto);

	public void delete(int id);

}
