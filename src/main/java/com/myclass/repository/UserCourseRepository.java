package com.myclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.entity.UserCourse;

@Repository
public interface UserCourseRepository extends JpaRepository<UserCourse, Integer>{

	@Query("SELECT uc FROM UserCourse uc WHERE uc.id.courseId = :courseId AND uc.id.userId = :userId")
	UserCourse findByCourseIdAndUserId(@Param("courseId") Integer courseId, @Param("userId") Integer userId);
}
