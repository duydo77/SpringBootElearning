package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Video;

@Repository
public interface VideoRepository extends JpaRepository<Video, Integer> {
	
	public List<Video> findByCourseId(int courseId);

}
