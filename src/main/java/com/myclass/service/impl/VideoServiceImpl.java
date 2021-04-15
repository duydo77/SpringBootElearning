package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myclass.dto.VideoDto;
import com.myclass.entity.Video;
import com.myclass.repository.VideoRepository;
import com.myclass.service.VideoService;

@Service
@Transactional(rollbackOn = Exception.class)
public class VideoServiceImpl implements VideoService{

	@Autowired
	VideoRepository videoRepository;
	
	@Override
	public List<VideoDto> findByCourseId(int courseId) {
		List<Video> entities = videoRepository.findByCourseId(courseId); 
		List<VideoDto> dtos = new ArrayList<VideoDto>();
		for(Video entity: entities) {
			dtos.add(new VideoDto(entity.getId(), entity.getTitle(), entity.getUrl(), entity.getTimeCount(), entity.getCourseId()));
		}
		return dtos;
	}

}
