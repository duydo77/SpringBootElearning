package com.myclass.service;

import java.util.List;

import com.myclass.dto.VideoDto;

public interface VideoService {
	List<VideoDto> findByCourseId(int courseId);
	
	void add(VideoDto dto);
	
	void update(VideoDto dto);
	
	VideoDto findById(int id);
	
	void delete(int id);
}
