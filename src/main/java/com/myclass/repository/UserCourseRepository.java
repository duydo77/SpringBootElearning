package com.myclass.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myclass.entity.UserCourse;

public interface UserCourseRepository extends JpaRepository<UserCourse, Integer>{

}
