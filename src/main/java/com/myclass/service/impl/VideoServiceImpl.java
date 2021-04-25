package com.myclass.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.myclass.dto.ImageDto;
import com.myclass.dto.UploadVideoDto;
import com.myclass.dto.UploadVideoFileDto;
import com.myclass.dto.VideoDto;
import com.myclass.dto.VideoFileDto;
import com.myclass.dto.VideoDto;
import com.myclass.entity.User;
import com.myclass.entity.Video;
import com.myclass.entity.Video;
import com.myclass.repository.UserRepository;
import com.myclass.repository.VideoRepository;
import com.myclass.service.VideoService;

@Service
@Transactional(rollbackOn = Exception.class)
public class VideoServiceImpl implements VideoService{

	private static String UPLOAD_DIR = "videos";
	
	@Autowired
	VideoRepository videoRepository;
	
	@Autowired
	UserRepository userRepository;
	
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
	
	@Override
	public void add(UploadVideoDto dto) {
		
		System.out.println("title " + dto.getTitle());
		System.out.println("base64 video " + dto.getVideoAsBase64());
	}
	
	@Override
	public void add(UploadVideoFileDto dto, HttpServletRequest req) {
		try {
			String fileName = dto.getFile().getOriginalFilename();
			String path = req.getServletContext().getRealPath("") + UPLOAD_DIR + File.separator + fileName;
			
			saveFile(dto.getFile().getInputStream(), path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void update(int videoId, MultipartFile file) {
		try {
//			String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
			String fileName = "video" + String.valueOf(videoId);
			String fileNameWithExt = fileName + ".mp4";
			String path = "src/main/resources/static/videos/" + fileNameWithExt;
			saveFile(file.getInputStream(), path);
			Video entity = videoRepository.getOne(videoId);
			entity.setUrl(fileName);
			System.out.println(entity.getUrl());
			videoRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveFile(InputStream inputStream, String path) {
		try {
			OutputStream outputStream = new FileOutputStream(new File(path));
			int read = 0;
			byte[] bytes = new byte[1024];
			while((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public VideoFileDto getVideoFile(String videoName) throws IOException {
		Path filename = Paths.get("src/main/resources/static/videos", videoName);
		byte[] buffer = Files.readAllBytes(filename);
		ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);
		VideoFileDto dto = new VideoFileDto(buffer.length, byteArrayResource);
		
		return dto;
	}

	@Override
	public int addAndReturnId(VideoDto dto) {
		Video entity = new Video(dto.getTitle(), dto.getUrl(), dto.getTimeCount(), dto.getCourseId());
		return videoRepository.saveAndFlush(entity).getId();
	}

	@Override
	public String add(MultipartFile file) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
