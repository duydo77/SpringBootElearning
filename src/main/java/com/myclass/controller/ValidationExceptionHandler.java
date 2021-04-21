package com.myclass.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.propertyeditors.FileEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ValidationExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		// Tạo danh sách nội dung trả về
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("timestamp", new Date());
		body.put("status", status.value());
		// Lấy danh sách lỗi lưu vào List<String>
		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldError = bindingResult.getFieldErrors();
		List<String> errors = new ArrayList<String>();
		for (FieldError item: fieldError) {
			errors.add(item.getDefaultMessage());
		}
		// Thêm danh sách lỗi vào nội dung trả về
		body.put("error", errors);
		return new ResponseEntity<Object>(body, headers, status);	
	}

}
