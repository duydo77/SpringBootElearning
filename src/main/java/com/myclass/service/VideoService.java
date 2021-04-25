package com.myclass.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.myclass.dto.UploadVideoDto;
import com.myclass.dto.UploadVideoFileDto;
import com.myclass.dto.VideoDto;
import com.myclass.dto.VideoFileDto;

public interface VideoService {
	List<VideoDto> findByCourseId(int courseId);
	
	public List<VideoDto> findAll();

	public VideoDto findById(int id);

	public void update(int id, VideoDto dto);
	
	public void update(int id, MultipartFile file);

	public void add(VideoDto dto);
	
	public void add(UploadVideoDto dto);

	public void add(UploadVideoFileDto dto, HttpServletRequest req);
	
	public int addAndReturnId(VideoDto dto);
	//test
	public String add(MultipartFile file);
	
	public void delete(int id);
	
	public VideoFileDto getVideoFile(String videoName) throws IOException;
}
