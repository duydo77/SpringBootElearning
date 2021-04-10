package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.entity.Target;

@Repository
public interface TargetRepository extends JpaRepository<Target, Integer> {

	@Query("SELECT t FROM Target t WHERE t.courseId = :courseId")
	List<Target> findByCourseId(@Param("courseId") int courseId);
	
}
