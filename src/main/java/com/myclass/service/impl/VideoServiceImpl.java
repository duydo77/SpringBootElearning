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
			dtos.add(new VideoDto(
							entity.getId(), 
							entity.getTitle(), 
							entity.getUrl(), 
							entity.getTimeCount(), 
							entity.getCourseId()));
		}
		return dtos;
	}

	@Override
	public void add(VideoDto dto) {
		Video entity = new Video(dto.getTitle(), dto.getUrl(), dto.getTimeCount(), dto.getCourseId());
		videoRepository.save(entity);
	}

	@Override
	public void update(VideoDto dto) {
		Video entity = new Video(dto.getId() ,dto.getTitle(), dto.getUrl(), dto.getTimeCount(), dto.getCourseId());
		videoRepository.save(entity);
	}

	@Override
	public VideoDto findById(int id) {
		Video entity = videoRepository.getOne(id);
		return new VideoDto(entity.getId(), entity.getTitle(), entity.getUrl(), entity.getTimeCount(), entity.getCourseId());
	}

	@Override
	public void delete(int id) {
		Video entity = videoRepository.getOne(id);
		videoRepository.delete(entity);
	}
	
	@Override
	public List<VideoDto> findAll() {
		List<Video> entities = videoRepository.findAll();
		List<VideoDto> dtos = new ArrayList<VideoDto>();

		for (Video entity : entities) {
			dtos.add(new VideoDto(entity.getId(), 
									entity.getTitle(), 
									entity.getUrl(), 
									entity.getTimeCount(), 
									entity.getCourseId()));
		}

		return dtos;
	}

	@Override
	public VideoDto findById(int id) {
		Video entity = videoRepository.getOne(id);
		return new VideoDto(entity.getId(), 
							entity.getTitle(), 
							entity.getUrl(), 
							entity.getTimeCount(), 
							entity.getCourseId());
	}

	@Override
	public void update(int id, VideoDto dto) {
		if (videoRepository.existsById(id)) {
			Video entity = videoRepository.getOne(dto.getId());
			if (entity == null)
				return;
			entity.setTitle(dto.getTitle());
			entity.setCourseId(dto.getCourseId());
			entity.setTimeCount(dto.getTimeCount());
			entity.setUrl(dto.getUrl());
			videoRepository.save(entity);
		}
	}

	@Override
	public void add(VideoDto dto) {
		Video entity = new Video(dto.getTitle(), 
									dto.getUrl(), 
									dto.getTimeCount(), 
									dto.getCourseId());
		videoRepository.save(entity);
	}

	@Override
	public void delete(int id) {
		videoRepository.deleteById(id);
	}

}
