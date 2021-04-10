package com.myclass.controller.admin;

import java.util.List;

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

import com.myclass.dto.CategoryDto;
import com.myclass.service.CategoryService;

@RestController
@RequestMapping("api/admin/category")
public class AdminCategoryController {

	CategoryService categoryService;
	
	AdminCategoryController(CategoryService categoryService){
		this.categoryService = categoryService;
	}
	
	// lấy thông tin hiển thị danh sách 
	@GetMapping("")
	public Object get() {
		try {
			List<CategoryDto> dtos = categoryService.findAll();
			return new ResponseEntity<Object>(dtos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	// lưu category mới 
	@PostMapping("")
	public Object post(@RequestBody CategoryDto body) {
		try {
			categoryService.add(body);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	// lấy thông tin một category rồi đó lưu thông tin chính sửa với id
	@GetMapping("/{id}")
	public Object get(@PathVariable("id") int id) {
		try {
			CategoryDto dto = categoryService.findById(id);
			return new ResponseEntity<Object>(dto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("/{id}")
	public Object put(@PathVariable("id") int id, @RequestBody CategoryDto dto) {
		try {
			categoryService.update(id, dto);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
	
	// xoá category
	@DeleteMapping("/{id}")
	public Object delete(@PathVariable("id") int id) {
		try {
			categoryService.delete(id);
			return new ResponseEntity<Object>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
	}
}
