package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.cateId = ca.id")
	public List<CourseDto> findAllWithCate();
}
