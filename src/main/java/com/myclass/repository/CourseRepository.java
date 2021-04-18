package com.myclass.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.myclass.dto.CourseDto;
import com.myclass.entity.Course;
import com.myclass.entity.User;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {

	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.cateId = ca.id")
//	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.lectureCount, c.hourCount, c.viewCount, c.price, c.discount,"
//			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
//			+ " ca.icon, uc.id.userId) "
//			+ "FROM Course c JOIN Category ca JOIN UserCourse uc "
//			+ "ON c.cateId=ca.id AND uc.id.courseId=c.id AND uc.roleId=2")
	public List<CourseDto> findAllWithCate();
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name, ca.icon) "
			+ "FROM Course 		c "
			+ "JOIN Category 	ca ON c.cateId = ca.id "
			+ "JOIN UserCourse 	uc ON uc.id.courseId = c.id "
			+ "JOIN User 		u ON uc.id.userId = u.id "
			+ "AND 	u.id = :userid1")
	public List<CourseDto> findAllOfTeacher(@Param("userid1") Integer userId);
	
	// test
	@Query("SELECT c "
			+ "FROM Course 		c "
			+ "JOIN Category 	ca ON c.cateId = ca.id "
			+ "JOIN UserCourse 	uc ON uc.id.courseId = c.id "
			+ "JOIN User 		u ON uc.id.userId = u.id "
			+ "AND 	u.id = :userid "
			+ "AND 	c.id = :courseid")
	public Course test(@Param("userid") Integer userId, @Param("courseid") Integer courseId);
	@Query("SELECT u FROM UserCourse uc JOIN User u ON uc.id.courseId=:courseId AND uc.id.userId=u.id AND uc.roleId=2")
	public User findTeacher(@Param("courseId") int courseId);
	
	@Query("SELECT new com.myclass.dto.CourseDto(c.id, c.title, c.image, c.hourCount, c.viewCount, c.price, c.discount,"
			+ " c.promotionPrice, c.desc, c.content, c.cateId, c.lastUpdate, ca.name,"
			+ " ca.icon) FROM Course c JOIN Category ca ON c.id=:id AND c.cateId = ca.id")
	public CourseDto findById(@Param("id") int id);
}
