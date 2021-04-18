package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
	
	@Query("SELECT v FROM Video v WHERE v.courseId = :courseId")
	public List<Video> findByCourseId(@Param("courseId") int courseId);

}
