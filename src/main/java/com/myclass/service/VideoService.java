package com.myclass.service;

import java.util.List;

import com.myclass.dto.UploadVideoDto;
import com.myclass.dto.VideoDto;

public interface VideoService {
	List<VideoDto> findByCourseId(int courseId);
	
	public List<VideoDto> findAll();

	public VideoDto findById(int id);

	public void update(int id, VideoDto dto);

	public void add(VideoDto dto);
	
	public void add(UploadVideoDto dto);

	public void delete(int id);
}
