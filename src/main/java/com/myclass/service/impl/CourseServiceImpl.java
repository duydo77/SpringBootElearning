package com.myclass.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.myclass.dto.CategoryDto;
import com.myclass.dto.CourseDetailsDto;
import com.myclass.dto.CourseDto;
import com.myclass.dto.SearchDto;
import com.myclass.dto.TargetDto;
import com.myclass.dto.UserDetailsDto;
import com.myclass.dto.UserDto;
import com.myclass.dto.VideoDto;
import com.myclass.entity.Course;
import com.myclass.entity.Target;
import com.myclass.entity.User;
import com.myclass.entity.UserCourse;
import com.myclass.entity.UserCourseKey;
import com.myclass.entity.Video;
import com.myclass.repository.CourseRepository;
import com.myclass.repository.UserCourseRepository;
import com.myclass.service.CourseService;
import com.myclass.utils.DefaultPath;

@Service
@Scope("prototype")
@Transactional(rollbackOn = Exception.class)
public class CourseServiceImpl implements CourseService {

	private CourseRepository courseRepository;
	private UserCourseRepository userCourseRepository;

	@Autowired
	private ServletContext context;

	CourseServiceImpl(CourseRepository courseRepository, UserCourseRepository userCourseRepository) {
		this.courseRepository = courseRepository;
		this.userCourseRepository = userCourseRepository;
	}

	@Override
	public List<CourseDto> findAll() {
		List<CourseDto> courseDtos = courseRepository.findAllWithCate();
		System.out.println("sourse service impl " + context.getContextPath());
		for (CourseDto courseDto : courseDtos) {
			User teacher = courseRepository.findTeacher(courseDto.getId());
			courseDto.setTeacherId(teacher.getId());
			courseDto.setTeacherName(teacher.getFullname());
		}
		return courseDtos;
	}

	@Override
	public CourseDto findById(int id) {
		CourseDto courseDto = courseRepository.findById(id);
		System.out.println(courseDto.getLectureCount());
		User teacher = courseRepository.findTeacher(courseDto.getId());
		courseDto.setTeacherId(teacher.getId());
		courseDto.setTeacherName(teacher.getFullname());
		return courseDto;
	}

	@Override
	public void update(int id, CourseDto dto) {
		Course entity = courseRepository.getOne(dto.getId());
		if (entity == null)
			return;
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setImage(dto.getImage());
		entity.setHourCount(dto.getHourCount());
		entity.setViewCount(dto.getViewCount());
		entity.setPrice(dto.getPrice());
		entity.setDiscount(dto.getDiscount());
		entity.setPromotionPrice(dto.getPromotionPrice());
		entity.setDesc(dto.getDesc());
		entity.setContent(dto.getContent());
		entity.setCateId(dto.getCateId());
		entity.setLastUpdate(dto.getLastUpdate());
		courseRepository.save(entity);
	}

	@Override
	public void update(CourseDto dto) {

		Course entity = courseRepository.getOne(dto.getId());
		if (entity == null)
			return;
		entity.setId(dto.getId());
		entity.setTitle(dto.getTitle());
		entity.setImage(dto.getImage());
		entity.setHourCount(dto.getHourCount());
		entity.setViewCount(dto.getViewCount());
		entity.setPrice(dto.getPrice());
		entity.setDiscount(dto.getDiscount());
		entity.setPromotionPrice(dto.getPromotionPrice());
		entity.setDesc(dto.getDesc());
		entity.setContent(dto.getContent());
		entity.setCateId(dto.getCateId());
		entity.setLastUpdate(dto.getLastUpdate());
		courseRepository.save(entity);
	}

	@Override
	public void add(CourseDto dto) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = ((UserDetailsDto) principal).getId();
		int roleId = ((UserDetailsDto) principal).getRoleId();

		Course entity = new Course(dto.getId(), dto.getTitle(), dto.getImage(), dto.getLectureCount(),
				dto.getHourCount(), dto.getViewCount(), dto.getPrice(), dto.getDiscount(), dto.getPromotionPrice(),
				dto.getDesc(), dto.getContent(), dto.getCateId(), dto.getLastUpdate());
		System.out.println("asdsada");
		int id = courseRepository.saveAndFlush(entity).getId();
		System.out.println("Before nn table edeita");
		UserCourseKey userCourseKey = new UserCourseKey(userId, id);
		UserCourse userCourse = new UserCourse(userCourseKey, roleId);
		userCourseRepository.save(userCourse);
		System.out.println("After nn table");
	}

	@Override
	public void delete(int id) {
		courseRepository.deleteById(id);
		userCourseRepository.deleteById(id);
	}

//	@Override
//	public CourseDto findDetailById(int id) {
//		CourseDto dto = courseRepository.findDetailById(id);
//		return dto;
//	}

	@Override
	public CourseDetailsDto findDetailById(int courseId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int userId = ((UserDetailsDto) principal).getId();

		Course entity = courseRepository.findDetailById(userId, courseId);

		List<VideoDto> listVideo = new ArrayList<VideoDto>();
		for (Video video : entity.getVideos()) {
			VideoDto dto = new VideoDto(video.getId(), video.getTitle(), video.getUrl(), video.getTimeCount());
			listVideo.add(dto);
		}

		List<TargetDto> listTarget = new ArrayList<TargetDto>();
		for (Target target : entity.getTargets()) {
			TargetDto dto = new TargetDto(target.getId(), target.getTitle());
			listTarget.add(dto);
		}

		CourseDetailsDto dto = new CourseDetailsDto(
				new CourseDto(entity.getId(), entity.getTitle(), entity.getImage(), entity.getLectureCount(),
						entity.getHourCount(), entity.getViewCount(), entity.getPrice(), entity.getDiscount(),
						entity.getPromotionPrice(), entity.getDesc(), entity.getContent(), entity.getLastUpdate()),

				listVideo, listTarget,
				new UserDto(entity.getUserCourses().get(0).getUser().getId(),
						entity.getUserCourses().get(0).getUser().getFullname()),
				new CategoryDto(entity.getCategory().getId(), entity.getCategory().getName(),
						entity.getCategory().getIcon()));
		return dto;
	}

	@Override
	public List<CourseDto> findAllOfTeacher() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = ((UserDetailsDto) principal).getId();
		List<CourseDto> courseDtos = courseRepository.findAllOfTeacher(id);
		for (CourseDto courseDto: courseDtos) {
			courseDto.setImage(DefaultPath.imageCoursePath + courseDto.getImage());
		}
		return courseDtos;
	}

	@Override
	public List<CourseDto> findAllOfUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = ((UserDetailsDto) principal).getId();
		List<CourseDto> courseDtos = courseRepository.findAllOfTeacher(id);
		for (CourseDto courseDto : courseDtos) {
			courseDto.setImage(DefaultPath.imageCoursePath + courseDto.getImage());
			User teacher = courseRepository.findTeacher(courseDto.getId());
			courseDto.setTeacherId(teacher.getId());
			courseDto.setTeacherName(teacher.getFullname());
		}
		return courseDtos;
	}

	@Override
	public Course test(int courseId) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		int id = ((UserDetailsDto) principal).getId();
		return courseRepository.test(id, courseId);
	}

	@Override
	public List<CourseDto> findPromotion() {
		List<CourseDto> courseDtos = courseRepository.findPromotion();
		for (CourseDto courseDto : courseDtos) {
			courseDto.setImage(DefaultPath.imageCoursePath + courseDto.getImage());
			User teacher = courseRepository.findTeacher(courseDto.getId());
			System.out.println(courseDto.getId());
			courseDto.setTeacherId(teacher.getId());
			courseDto.setTeacherName(teacher.getFullname());
		}
		return courseDtos;
	}

	@Override
	public List<CourseDto> findPopular() {
		List<CourseDto> courseDtos = courseRepository.findNormal();
		for (CourseDto courseDto : courseDtos) {
			courseDto.setImage(DefaultPath.imageCoursePath + courseDto.getImage());
			User teacher = courseRepository.findTeacher(courseDto.getId());
			courseDto.setTeacherId(teacher.getId());
			courseDto.setTeacherName(teacher.getFullname());
		}
		return courseDtos;
	}

	@Override
	public List<CourseDto> findByCateId(int cateId) {
		List<CourseDto> dtos = courseRepository.findbyCateId(cateId);
		for(CourseDto dto: dtos) {
			dto.setImage(DefaultPath.imageCoursePath + dto.getImage());
		}
		return dtos;
	}
	
	@Override
	public List<CourseDto> search(String key) {
		List<CourseDto> dtos = courseRepository.search("%" + key + "%");
		for(CourseDto dto: dtos) {
			dto.setImage(DefaultPath.imageCoursePath + dto.getImage());
		}
		return dtos;
	}
	
	@Override
	public List<CourseDto> searchFilter(SearchDto searchDto) {
		List<CourseDto> dtos = courseRepository.searchCourse(searchDto);
		for(CourseDto dto: dtos) {
			dto.setImage(DefaultPath.imageCoursePath + dto.getImage());
		}
		return dtos;
	}
	
}
